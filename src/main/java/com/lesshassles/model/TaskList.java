package com.lesshassles.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class TaskList {
	public TaskList() {
		
	}
	
	public TaskList(String name) {
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

	@OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL)
	private Set<Task> tasks = new HashSet<Task>();

	public void addTask(Task task) {
		task.setTaskList(this);
		tasks.add(task);
	}
	
	public Set<Task> getTasks() {
		return Collections.unmodifiableSet(tasks);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		} else if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		} else {
			TaskList otherTaskList = (TaskList)obj;
			return this.getId().equals(otherTaskList.getId());
		}
	}
	
	@Override
	public int hashCode() {
		return getId();
	}
}
