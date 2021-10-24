package com.samirbenbouker.userservice.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samirbenbouker.userservice.domain.AppRole;
import com.samirbenbouker.userservice.domain.AppUser;
import com.samirbenbouker.userservice.repository.AppRoleRepository;
import com.samirbenbouker.userservice.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService{

	private final AppUserRepository appUserRepository;
	private final AppRoleRepository appRoleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public AppUserServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.appRoleRepository = appRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = getAppUserByUsername(username);
		if(user == null) {
			throw new IllegalStateException("USER NOT FOUND IN DATABASE");
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		
		return new User(user.getUsername(), user.getPassword(), authorities);
	}
	
	@Override
	public AppUser saveUser(AppUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return appUserRepository.save(user);
	}

	@Override
	public AppRole saveRole(AppRole role) {
		return appRoleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {	
		getAppUserByUsername(username).getRoles().add(getAppRoleByUsername(roleName));
	}

	@Override
	public AppUser getUser(String username) {
		return getAppUserByUsername(username);
	}
	
	public AppUser getAppUserByUsername(String username) {
		ArrayList<AppUser> allUsers = (ArrayList<AppUser>) appUserRepository.findAll();
		
		for (AppUser appUser : allUsers) {
			if(appUser.getUsername().equals(username) || appUser.getName().equals(username)) {
				return appUser;
			}
		}
		
		return new AppUser("error","error","error", null); 
	}
	
	public AppRole getAppRoleByUsername(String roleName) {
		ArrayList<AppRole> allRoles = (ArrayList<AppRole>) appRoleRepository.findAll();
		
		for (AppRole appRole : allRoles) {
			if(appRole.getName().equals(roleName)) {
				return appRole;
			}
		}
		
		return new AppRole(null,"error"); 
	}
	

	@Override
	public List<AppUser> getAllUsers() {
		return appUserRepository.findAll();
	}
}
