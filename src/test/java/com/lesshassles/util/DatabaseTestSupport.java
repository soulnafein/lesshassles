package com.lesshassles.util;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-hibernate.xml"})
@Transactional
public class DatabaseTestSupport {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void populateDatabase(Object... elements) {
		for (Object element : elements) {
			sessionFactory.getCurrentSession().save(element);
		}
		sessionFactory.getCurrentSession().flush();
	}
	
	public <T> void populateDatabase(Collection<T> elements) {
		for (T element : elements) {
			sessionFactory.getCurrentSession().save(element);
		}
		sessionFactory.getCurrentSession().flush();
	}
	
	
}
