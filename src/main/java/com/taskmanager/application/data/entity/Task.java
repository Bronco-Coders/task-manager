package com.taskmanager.application.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class Task extends AbstractEntity {

    private String taskName;
    private String taskLabel;
    private String taskPriority;
    private LocalDate taskDueDate;
    private String taskStatus;

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
    public String getTaskPriority() {
        return taskPriority;
    }
    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }
    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }
    public void setTaskDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }
    public String getTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    
}
