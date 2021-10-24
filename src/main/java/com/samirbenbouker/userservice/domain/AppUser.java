package com.samirbenbouker.userservice.domain;

import java.util.*;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.AUTO
	)
	@SequenceGenerator(name = "app_user_sequence", sequenceName = "app_user_sequence", allocationSize = 1)
	private Long id;
	private String name;
	private String username;
	private String password;
	
	// Reload page every time when change/add a role
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<AppRole> roles = new ArrayList<AppRole>();
	
	public AppUser() {}

	@Autowired
	public AppUser(Long id, String name, String username, String password, Collection<AppRole> roles) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public AppUser(String name, String username, String password, Collection<AppRole> roles) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<AppRole> getRoles() {
		return this.roles;
	}

	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}
}
