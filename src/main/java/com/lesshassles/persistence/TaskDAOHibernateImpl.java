package com.lesshassles.persistence;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lesshassles.model.Task;

@Repository
public class TaskDAOHibernateImpl implements TaskDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public Task findById(Integer id) {
	return (Task) sessionFactory.getCurrentSession().get(Task.class, id);
    }

}
