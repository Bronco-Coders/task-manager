package com.taskmanager.application.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class Task extends AbstractEntity {

    private String taskName;
    private String taskLabel;
    private Integer taskPriority;
    private LocalDate taskDueDate;

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskLabel() {
        return taskLabel;
    }
    public void setTaskLabel(String taskLabel) {
        this.taskLabel = taskLabel;
    }
    public Integer getTaskPriority() {
        return taskPriority;
    }
    public void setTaskPriority(Integer taskPriority) {
        this.taskPriority = taskPriority;
    }
    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }
    public void setTaskDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

}
