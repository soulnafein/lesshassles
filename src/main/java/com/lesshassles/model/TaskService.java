package com.lesshassles.model;

import java.util.Date;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class follows the Service Layer pattern
 * It mainly provide CRUD functionalities for the Task class
 * @author david
 */
@Service
@Transactional
public class TaskService {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Retrieve a task for the specified id
	 * @param id
	 * @throws ObjectNotFoundException when providing an invalid id
	 */
	public Task findById(Integer id) {
		return (Task) sessionFactory.getCurrentSession().load(Task.class, id);
	}
	
	public void assignTaskToUser(Integer taskId, User assignee) {
		Task task = findById(taskId);
		task.setAssignee(assignee);
	}

	public void deassignTask(Integer taskId) {
		Task task = findById(taskId);
		task.setAssignee(null);
	}

	/**
	 * Retrieves a list of tasks assigned to a user sorted by deadline
	 * @param loggedUser
	 */
	public List<Task> getTasksAssignedToUser(User loggedUser) {
		return (List<Task>)sessionFactory.getCurrentSession()
					.createQuery(	"select task from Task task " +
									"join task.taskList taskList " +
									"where task.assignee = :loggedUser " +
									"or (task.assignee is null and taskList.owner = :loggedUser) " +
									"order by task.deadline desc")
					.setEntity("loggedUser", loggedUser)
					.list();
	}

	public void changeTaskStatus(Integer taskId, TaskStatus status) {
		Task task = findById(taskId);
		task.setStatus(status);
	}

	/**
	 * Retrieve a list of tasks owned by a specific User and
	 * assigned to other users. The result is sorted by deadline.
	 * @param loggedUser
	 */
	public List<Task> getTasksAssignedToOtherUsers(User loggedUser) {
		return (List<Task>)sessionFactory.getCurrentSession()
		.createQuery(	"select task from Task task " +
						"join task.taskList taskList " +
						"where task.assignee <> :loggedUser " +
						"and task.assignee is not null and taskList.owner = :loggedUser " +
						"order by task.deadline desc")
		.setEntity("loggedUser", loggedUser)
		.list();
	}

	public void changeDeadline(Integer taskId, Date deadline) {
		Task task = findById(taskId);
		task.setDeadline(deadline);
	}

}
