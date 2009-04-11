package com.lesshassles.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.lesshassles.util.DatabaseTestSupport;

public class UserDetailsServiceTest extends DatabaseTestSupport {
	@Autowired
	UserDetailsService userDetailsService;
	
	@Test
	public void shouldReturnValidUser() {
		User aUser = new User("email@test.tst").setFullname("A user");
		populateDatabase(aUser);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(aUser.getEmail());
		
		assertNotNull(userDetails);
	}
	
	@Test
	public void shouldThrowUsernameNotFoundExceptionWhenProvidingAnInvalidEmail() {
		try {
			userDetailsService.loadUserByUsername("thisEmailDoesNotExist@test.tst");
			fail();
		} catch(UsernameNotFoundException ex) {
			
		}
	}
}
