package com.lesshassles.persistence;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lesshassles.model.TaskList;

@Repository
public class TaskListDAOHibernateImpl implements TaskListDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Integer makePersistent(TaskList taskList) {
		return (Integer)sessionFactory.getCurrentSession().save(taskList);
	}

	public TaskList findById(Integer id) {
		return (TaskList)sessionFactory.getCurrentSession().get(TaskList.class, id);
	}

}
