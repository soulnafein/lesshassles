package com.lesshassles.web;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lesshassles.model.User;

@Service
public class AuthenticationService {

	public User getAuthenticatedUser() {
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
