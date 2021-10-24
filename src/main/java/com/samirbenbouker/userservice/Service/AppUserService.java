package com.samirbenbouker.userservice.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.samirbenbouker.userservice.domain.AppRole;
import com.samirbenbouker.userservice.domain.AppUser;

public interface AppUserService {
	
	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String username, String roleName);
	public AppUser getUser(String username);
	public List<AppUser> getAllUsers();
	
}
