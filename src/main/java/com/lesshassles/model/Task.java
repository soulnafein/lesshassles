package com.lesshassles.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;

/**
 * Entity representing an entry in a Tasklist
 * It is mapped using Hibernate annotations for
 * O/R mappings and data constraints
 * @author david
 *
 */
@Entity
public class Task {

	public Task() {
		this.description = "New task";
		this.status = TaskStatus.Open;
	}

	public Task(String description) {
		this.description = description;
		this.status = TaskStatus.Open;
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
	
	@Enumerated
	@Column(nullable = false)
	private TaskStatus status;

	private Date deadline;
	
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
	
	public TaskStatus getStatus() {
		return status;
	}
	
	public Task setStatus(TaskStatus status) {
		this.status = status;
		return this;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	
	public Task setDeadline(Date deadline) {
		this.deadline = deadline;
		return this;
	}
	
	/**
	 * Two tasks are considered equals if they 
	 * are part of the same Tasklists and they have the same description
	 */
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
