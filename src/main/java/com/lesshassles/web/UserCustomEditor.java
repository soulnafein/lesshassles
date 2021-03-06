package com.lesshassles.web;

import java.beans.PropertyEditorSupport;

import com.lesshassles.model.User;
import com.lesshassles.model.UserService;

/**
 * This class is used by Spring in order to automatically
 * bind a string containing the id of a user posted in a form
 * @author david
 *
 */
public class UserCustomEditor extends PropertyEditorSupport {
	private UserService userService;
	
	public UserCustomEditor(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Integer id = Integer.parseInt(text);
		User user = userService.findById(id);
		setValue(user);
	}
	
	@Override
	public String getAsText() {
		User user = (User)getValue();
		return user.getId().toString();
	}
}
