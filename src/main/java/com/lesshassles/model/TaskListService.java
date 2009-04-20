package com.lesshassles.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class follows the Service Layer pattern
 * It mainly provide CRUD functionalities for the Tasklist class
 * @author david
 *
 */
@Service
@Transactional
public class TaskListService {
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Persist a Tasklist in to the database
	 * @param TaskList taskList
	 * @return the sequence id generated by the database
	 */
	public Integer save(TaskList taskList) {
		if (taskList == null)
			throw new IllegalArgumentException(String.format(
					"%s save(TaskList taskList): taskList must be not null",
					this.getClass().getName()));

		taskList.setName(removeDuplicateWhitespacesAndTrim(taskList.getName()));

		return (Integer) sessionFactory.getCurrentSession().save(taskList);
	}

	/**
	 * Used to update the state of a Tasklist into the database
	 * @param TaskList taskList
	 */
	public void update(TaskList taskList) {
		sessionFactory.getCurrentSession().update(taskList);
	}

	/**
	 * 
	 * @param id
	 * @param owner
	 * @return The tasklist for the specified owner and id 
	 * 			null if there are no results
	 */
	public TaskList findByIdAndOwner(Integer id, User owner) {
		return (TaskList) sessionFactory.getCurrentSession()
			.createQuery("from TaskList where id = :id and owner = :owner")
			.setInteger("id", id)
			.setEntity("owner", owner)
			.uniqueResult();
	}

	/**
	 * Retrieve a list of tasklist owned by the specified User
	 * @param owner
	 */
	public List<TaskList> findByOwner(User owner) {
		return sessionFactory.getCurrentSession().createQuery("from TaskList where owner = :owner")
			.setEntity("owner", owner)
			.list();
	}
	
	private String removeDuplicateWhitespacesAndTrim(String name) {
		String patternStr = "\\s+";
		String replaceStr = " ";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(name);
		return matcher.replaceAll(replaceStr).trim();
	}

	public void delete(TaskList taskList) {
		sessionFactory.getCurrentSession().delete(taskList);
	}
}
