package com.lesshassles.model;

import javax.persistence.*;

import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * Implementation of the UserDetails interface
 * required by Spring Security for validation.
 * It represent a User of our system
 * @author david
 *
 */
@Entity
public class User implements UserDetails {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Email @NotNull @NotEmpty
	private String email;
	
	private String password;
	
	@Column(nullable=false)
	private String fullname;
	
	public User() {}
	
	public User(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public User setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getPassword() {
		return password;
	}
	
	public String getFullname() {
		return fullname;
	}

	public User setFullname(String fullname) {
		this.fullname = fullname;
		return this;
	}

	public GrantedAuthority[] getAuthorities() {
		return new GrantedAuthority[] { new UserRole() };
	}

	public String getUsername() {
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
	
	/**
	 * Two User instances are considered equal when
	 * their email is the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		} else {
			User otherUser = (User) obj;
			return this.getEmail().equals(otherUser.getEmail());
		}
	}

	@Override
	public int hashCode() {
		return getEmail().hashCode();
	}

	private static final long serialVersionUID = -1617100713844967559L;
}
