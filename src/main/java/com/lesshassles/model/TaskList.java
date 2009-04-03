package com.lesshassles.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.Pattern;

@Entity
public class TaskList {
	
	TaskList() {}

	public TaskList(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	private Integer id;
	
	@Pattern(regex = "([a-zA-Z0-9.\\(\\)\\-']+\\s)*[a-zA-Z0-9.\\(\\)\\-']+")
	private String name;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private User owner;

	@OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL)
	private Set<Task> tasks = new HashSet<Task>();

	public Integer getId() {
		return id;
	}

	public TaskList setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public TaskList setName(String name) {
		this.name = name;
		return this;
	}

	public User getOwner() {
		return owner;
	}

	public TaskList setOwner(User owner) {
		this.owner = owner;
		return this;
	}

	public TaskList addTask(Task task) {
		task.setTaskList(this);
		tasks.add(task);
		return this;
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
			TaskList otherTaskList = (TaskList) obj;
			return this.getId().equals(otherTaskList.getId());
		}
	}

	@Override
	public int hashCode() {
		return getId() == null ? System.identityHashCode(this) : getId()
				.hashCode();
	}
}
