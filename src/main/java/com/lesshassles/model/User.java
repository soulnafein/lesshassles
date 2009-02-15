package com.lesshassles.model;

import javax.persistence.*;

import org.hibernate.validator.Email;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public GrantedAuthority[] getAuthorities() {
		return new GrantedAuthority[] { new UserRole() };
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	private static final long serialVersionUID = -1617100713844967559L;
}
