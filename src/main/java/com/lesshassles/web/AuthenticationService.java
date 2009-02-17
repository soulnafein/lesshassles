package com.lesshassles.web;

import com.lesshassles.model.User;

public interface AuthenticationService {
	User getAuthenticatedUser();
}
