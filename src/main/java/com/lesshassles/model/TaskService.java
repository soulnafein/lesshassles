package com.lesshassles.model;

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

}
