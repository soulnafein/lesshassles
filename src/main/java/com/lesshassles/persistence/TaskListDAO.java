package com.lesshassles.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lesshassles.model.TaskList;
import com.lesshassles.model.User;

@Repository
public class TaskListDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Integer makePersistent(TaskList taskList) {
		return (Integer) sessionFactory.getCurrentSession().save(taskList);
	}

	public void update(TaskList taskList) {
		sessionFactory.getCurrentSession().update(taskList);
	}

	public TaskList findByIdAndOwner(Integer id, User owner) {
		return (TaskList) sessionFactory.getCurrentSession()
				.createQuery("from TaskList where id = :id and owner = :owner")
				.setInteger("id", id)
				.setEntity("owner", owner)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TaskList> findByOwner(User owner) {
		return sessionFactory.getCurrentSession().createQuery("from TaskList where owner = :owner")
				.setEntity("owner", owner)
				.list();
	}
}
