package com.lesshassles.model;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.Pattern;

import com.lesshassles.model.exceptions.DuplicateObjectException;

/**
 * Entity representing a Tasklist owned by a User
 * @author david
 *
 */
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
	@Sort(type=SortType.COMPARATOR, comparator=DeadlineComparator.class)
	private SortedSet<Task> tasks = new TreeSet<Task>(new DeadlineComparator());

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

	/**
	 * All the tasks added to a tasklist
	 * will be automatically cascaded in the database
	 * when saving their tasklist
	 * @throws DuplicateObjectException when trying to add a duplicate task
	 * @param task
	 */
	public TaskList addTask(Task task) {
		task.setTaskList(this);
		if (!tasks.add(task)) {
			throw new DuplicateObjectException();
		}
		return this;
	}

	public SortedSet<Task> getTasks() {
		return Collections.unmodifiableSortedSet(tasks);
	}

	/**
	 * Two tasklists are considered equals if they have the same id
	 */
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
