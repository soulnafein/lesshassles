package com.lesshassles.model;

import javax.persistence.*;

@Entity
public class Task {
	public Task() {
		
	}
	
	public Task(String name) {
		this.name = name;
	}
	
	@Id @GeneratedValue
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
    @JoinColumn(name="taskListId", nullable=false)
    private TaskList taskList;
	public TaskList getTaskList() {
		return taskList;
	}
	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}
	
}
