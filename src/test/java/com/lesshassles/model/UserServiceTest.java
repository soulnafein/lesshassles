package com.lesshassles.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lesshassles.util.DatabaseTestSupport;

public class UserServiceTest extends DatabaseTestSupport {
	@Autowired
	UserService userService;
	
	@Test
	public void shouldReturnValidUser() {
		User aUser = new User("email@test.tst").setFullname("A user");
		populateDatabase(aUser);
		
		User user = userService.findById(aUser.getId());
		
		assertNotNull(user);
		assertEquals(aUser, user);
	}
	
	@Test
	public void shouldReturnNullWhenProvidingAnInvalidUserId() {
		User user = userService.findById(0);
		
		assertNull(user);
	}
	
	@Test
	public void shouldReturnAllActiveUsers() {
		Collection<User> usersStoredInDatabase = new ArrayList<User>();
		usersStoredInDatabase.add(new User("email1@test.tst").setFullname("user1"));
		usersStoredInDatabase.add(new User("email2@test.tst").setFullname("user2"));
		usersStoredInDatabase.add(new User("email3@test.tst").setFullname("user3"));
		populateDatabase(usersStoredInDatabase);
		
		Collection<User> users = userService.findAllActiveUsers();
		
		assertEquals(usersStoredInDatabase.size(), users.size());
	}
}
