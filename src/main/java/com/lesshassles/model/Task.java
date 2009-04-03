package com.lesshassles.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;

@Entity
public class Task {

	Task() {}

	public Task(String description) {
		this.description = description;
	}

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	@Pattern(regex = "([a-zA-Z0-9.\\(\\)\\-']+\\s)*[a-zA-Z0-9.\\(\\)\\-']+")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "taskListId", nullable = false)
	private TaskList taskList;
	
	@ManyToOne
	@JoinColumn(name = "assigneeId", nullable = true) 
	private User assignee;

	public Integer getId() {
		return id;
	}

	public Task setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Task setDescription(String description) {
		this.description = description;
		return this;
	}


	public TaskList getTaskList() {
		return taskList;
	}

	public Task setTaskList(TaskList taskList) {
		this.taskList = taskList;
		return this;
	}
	
	public User getAssignee() {
		return assignee;
	}
	
	public Task setAssignee(User assignee) {
		this.assignee = assignee;
		return this;
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
