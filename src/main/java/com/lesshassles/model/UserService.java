package com.lesshassles.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class UserService {
	@Autowired
	private SessionFactory sessionFactory;
	
	public User findById(Integer id) {
		return (User)sessionFactory.getCurrentSession().get(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllActiveUsers() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}

}
