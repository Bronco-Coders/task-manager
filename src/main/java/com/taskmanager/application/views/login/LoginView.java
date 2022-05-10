package com.taskmanager.application.views.login;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

import com.taskmanager.application.data.entity.User;
import com.taskmanager.application.data.service.UserService;
import com.taskmanager.application.views.tasks.TasksView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
//@PermitAll
@PageTitle("Login")
@Route(value = "")
public class LoginView extends Composite {
	@Resource
	private UserService userService;
	
	@Override
	protected Component initContent() {
		TextField username = new TextField("Username");
		PasswordField password1 = new PasswordField("Password");
		VerticalLayout layout = new VerticalLayout(
			new H2("Login"),
			username,
			password1,
			new HorizontalLayout(
			new Button("Login", clickevent -> login(
					username.getValue(),
					password1.getValue()
					)),
					
			
			new Button("Register", clickevent -> goTo(
					))
			) 
			);
			layout.setAlignItems(Alignment.CENTER);
		return layout;
				
	}
	
	private void login(String username, String password1) {
		UI.getCurrent().navigate(TasksView.class);
		
	}
    private void goTo(){
        UI.getCurrent().navigate(RegisterView.class);

    }
 
}
