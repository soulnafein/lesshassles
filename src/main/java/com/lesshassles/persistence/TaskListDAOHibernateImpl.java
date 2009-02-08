package com.lesshassles.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lesshassles.model.TaskList;

@Repository
public class TaskListDAOHibernateImpl implements TaskListDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public Integer makePersistent(TaskList taskList) {
	return (Integer) sessionFactory.getCurrentSession().save(taskList);
    }

    public void update(TaskList taskList) {
	sessionFactory.getCurrentSession().update(taskList);
    }

    public TaskList findById(Integer id) {
	return (TaskList) sessionFactory.getCurrentSession().get(
		TaskList.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<TaskList> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from TaskList").list();
	}
}
