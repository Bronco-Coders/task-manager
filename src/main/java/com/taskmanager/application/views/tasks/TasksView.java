package com.taskmanager.application.views.tasks;

import com.taskmanager.application.data.entity.Task;
import com.taskmanager.application.data.service.TaskService;
import com.taskmanager.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@CssImport(value = "./themes/taskmanager/components/vaadin-grid.css", themeFor = "vaadin-grid")
@PageTitle("Tasks")
@Route(value = "taskManager/:taskID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "/tasks", layout = MainLayout.class)
@PermitAll
public class TasksView extends Div implements BeforeEnterObserver {

    private final String TASK_ID = "taskID";
    private final String TASK_EDIT_ROUTE_TEMPLATE = "taskManager/%s/edit";
    //private static String task_status;

    private Grid<Task> grid = new Grid<>(Task.class, false);

    private TextField taskName;
    private TextField taskLabel;
    private Select<String> taskPriority;
    private DatePicker taskDueDate;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button complete = new Button("Complete");

    private BeanValidationBinder<Task> binder;

    private Task task;

    private final TaskService taskService;

    @Autowired
    public TasksView(TaskService taskService) {
        this.taskService = taskService;
        addClassNames("tasks-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        // Configure Grid
        grid.addColumn("taskName").setAutoWidth(true);
        grid.addColumn("taskLabel").setAutoWidth(true);
        grid.addColumn("taskPriority").setAutoWidth(true);
        grid.addColumn("taskDueDate").setAutoWidth(true);
        grid.addColumn("taskStatus").setAutoWidth(true);
        // grid.addColumn(createStatusComponentRenderer("In Progress")).setHeader("Task
        // Status")
        // .setAutoWidth(true);
        grid.setClassNameGenerator(task -> {
            if (task.getTaskStatus().equals("Completed")) {
                return "complete";
            }
            if (task.getTaskStatus().equals("Late")) {
                return "late";
            }
            return null;
        });

        // grid.addColumn(createStatusComponentRenderer("In Progress")).setHeader("Task
        // Status")
        // .setAutoWidth(true);
        grid.setItems(query -> taskService.list(
                    PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))
                ).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // multiselection mode to add checkbox next to each entry
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        // populate form when a row is selected by checkbox
        grid.asMultiSelect().addValueChangeListener(val -> {
            new ArrayList<>(grid.getSelectedItems()).forEach(row -> {
                if (val.getValue() != null) {
                    UI.getCurrent().navigate(String.format(TASK_EDIT_ROUTE_TEMPLATE, row.getId()));
                } else {
                    clearForm();
                    UI.getCurrent().navigate(TasksView.class);
                }
            });
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Task.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            grid.deselectAll();
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.task == null) {
                    this.task = new Task();
                }

                binder.writeBean(this.task);
                if (this.task.getTaskName().length() < 1 || this.task.getTaskName() == null
                        || this.task.getTaskDueDate() == null) {
                    Notification.show("Task Not Saved");

                } else {
                    if (this.task.getTaskDueDate().compareTo(LocalDate.now()) < 0)
                        this.task.setTaskStatus("Late");
                    else
                        this.task.setTaskStatus("In Progress");

                    taskService.update(this.task);
                    grid.deselectAll();
                    clearForm();
                    refreshGrid();
                    Notification.show("Task details stored.");
                    UI.getCurrent().navigate(TasksView.class);
                }

            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the task details.");
            }
        });

        delete.addClickListener(e -> {
            // delete selected items
            if (!grid.getSelectedItems().isEmpty()) {
                new ArrayList<>(grid.getSelectedItems()).forEach(row -> {
                    grid.deselectAll();
                    taskService.deleteTask(row);
                    clearForm();
                    refreshGrid();
                });
            }
        });

        complete.addClickListener(e -> {
            // mark selected item(s) as completed
            if (!grid.getSelectedItems().isEmpty()) {
                new ArrayList<>(grid.getSelectedItems()).forEach(entry -> {
                    grid.deselectAll();
                    // createStatusComponentRenderer("Completed");
                    entry.setTaskStatus("Completed");
                    taskService.update(entry);
                    clearForm();
                    refreshGrid();
                });
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> taskId = event.getRouteParameters().get(TASK_ID);
        if (taskId.isPresent()) {
            Optional<Task> taskFromBackend = taskService.get(taskId.get());
            if (taskFromBackend.isPresent()) {
                populateForm(taskFromBackend.get());
            } else {
                Notification.show(String.format("The requested task was not found, ID = %s", taskId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(TasksView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        taskName = new TextField("Name");
        taskName.setRequiredIndicatorVisible(true);
        taskName.setErrorMessage("This field is required");

        taskLabel = new TextField("Label");

        // taskPriority = new TextField("Task Priority");
        taskPriority = new Select<>();
        taskPriority.setLabel("Priority");
        taskPriority.setItems("Low", "Medium", "High");
        taskPriority.setPlaceholder("Select Priority");
        taskPriority.setValue("Low");

        taskDueDate = new DatePicker("Due Date");
        taskDueDate.setRequiredIndicatorVisible(true);
        taskDueDate.setErrorMessage("This field is required");

        Component[] fields = new Component[] { taskName, taskLabel, taskPriority, taskDueDate };

        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        save.addClickShortcut(Key.ENTER);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        delete.addClickShortcut(Key.DELETE);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addClickShortcut(Key.ESCAPE);
        complete.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        //complete.getStyle().set("color", "rgb(247, 212, 16)");
        
        // add buttons to layouts
        VerticalLayout buttonsLayout = new VerticalLayout();
        HorizontalLayout buttonsRow1 = new HorizontalLayout();
        buttonsRow1.add(save, delete);
        HorizontalLayout buttonsRow2 = new HorizontalLayout();
        buttonsRow2.add(complete, cancel);
        buttonsLayout.add(buttonsRow1, buttonsRow2);
        editorLayoutDiv.add(buttonsLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
        // checking for late tasks
        if (!grid.getSelectedItems().isEmpty()) {
            new ArrayList<>(grid.getSelectedItems()).forEach(entry -> {
                grid.deselectAll();
                if (entry.getTaskDueDate().compareTo(LocalDate.now()) > 0) {
                    entry.setTaskStatus("Late");
                    taskService.update(entry);
                    clearForm();
                    refreshGrid();
                }
            });
        }
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Task value) {
        this.task = value;
        binder.readBean(this.task);

    }

    // private static ComponentRenderer<Span, Task>
    // createStatusComponentRenderer(String s) {
    // task_status = s;
    // return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    // }

    // // set the status of each task
    // private static final SerializableBiConsumer<Span, Task>
    // statusComponentUpdater = (span, task) -> {
    // task.setTaskStatus(task_status);
    // boolean isAvailable = "In Progress".equals(task.getTaskStatus());
    // String theme = String.format("badge %s", isAvailable ? "success" : "error");
    // span.getElement().setAttribute("theme", theme);
    // span.setText(task.getTaskStatus());
    // };
}
