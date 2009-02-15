package com.lesshassles.model;

import org.springframework.security.GrantedAuthority;

public class UserRole implements GrantedAuthority {
	public String getAuthority() {
		return "ROLE_USER";
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static final long serialVersionUID = 640479760116096399L;
}
