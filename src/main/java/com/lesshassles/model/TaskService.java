package com.lesshassles.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

	@Autowired
	private SessionFactory sessionFactory;

	public Task findById(Integer id) {
		return (Task) sessionFactory.getCurrentSession().get(Task.class, id);
	}
	
	public void assignTaskToUser(Integer taskId, User assignee) {
		Task task = findById(taskId);
		task.setAssignee(assignee);
	}

	public void deassignTask(Integer taskId) {
		Task task = findById(taskId);
		task.setAssignee(null);
	}

	public List<Task> getTasksAssignedToUser(User loggedUser) {
		return (List<Task>)sessionFactory.getCurrentSession()
					.createQuery(	"select task from Task task " +
									"join task.taskList taskList " +
									"where task.assignee = :loggedUser " +
									"or (task.assignee is null and taskList.owner = :loggedUser) ")
					.setEntity("loggedUser", loggedUser)
					.list();
	}

	public void changeTaskStatus(Integer taskId, TaskStatus status) {
		Task task = findById(taskId);
		task.setStatus(status);
	}

}
