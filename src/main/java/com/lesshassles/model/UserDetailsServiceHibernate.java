package com.lesshassles.model;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class UserDetailsServiceHibernate implements UserDetailsService {
	@Autowired
	private SessionFactory sessionFactory;

	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException, DataAccessException {
		User user = (User) sessionFactory.getCurrentSession()
							.createQuery("from User where email = :email")
							.setString("email", email)
							.uniqueResult();
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User with email %s does not exist!", email));
		}
		
		return user;
	}
}
