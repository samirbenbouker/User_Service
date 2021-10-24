package com.samirbenbouker.userservice.domain;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppRole {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.AUTO
	)
	private Long id;
	private String name;
	
	public AppRole() {}
	
	@Autowired
	public AppRole(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public AppRole(String name) {
		super();
		this.name = name;
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
}
