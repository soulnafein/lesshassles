package com.lesshassles.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Task {

    public Task() {
	
    }
    
    public Task(String description) {
	this.description = description;
    }

    @Id
    @GeneratedValue
    private Integer id;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Column(nullable = false)
    private String description;

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "taskListId", nullable = false)
    private TaskList taskList;

    public TaskList getTaskList() {
	return taskList;
    }

    public void setTaskList(TaskList taskList) {
	this.taskList = taskList;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	} else if (obj == null || obj.getClass() != this.getClass()) {
	    return false;
	} else {
	    Task otherTask = (Task) obj;
	    return this.getDescription().equals(otherTask.getDescription())
		    && this.getTaskList().equals(otherTask.getTaskList());
	}
    }

    @Override
    public int hashCode() {
	return getDescription().hashCode();
    }
}
