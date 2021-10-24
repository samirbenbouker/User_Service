package com.samirbenbouker.userservice;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.samirbenbouker.userservice.Service.AppUserService;
import com.samirbenbouker.userservice.domain.AppRole;
import com.samirbenbouker.userservice.domain.AppUser;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			appUserService.saveRole(new AppRole(null, "ROLE_USER"));
			appUserService.saveRole(new AppRole(null, "ROLE_MANAGER"));
			appUserService.saveRole(new AppRole(null, "ROLE_ADMIN"));
			appUserService.saveRole(new AppRole(null, "ROLE_SUPER_ADMIN"));
			
			appUserService.saveUser(new AppUser(null, "John Travolta", "john", "1234", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Will Smith", "will", "1234", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Jim Carry", "jim", "1234", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Arnlod Schwarzenegger", "arnold", "1234", new ArrayList<>()));
			
			appUserService.addRoleToUser("john", "ROLE_USER");
			appUserService.addRoleToUser("john", "ROLE_MANAGER");
			appUserService.addRoleToUser("will", "ROLE_MANAGER");
			appUserService.addRoleToUser("jim", "ROLE_ADMIN");
			appUserService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
			appUserService.addRoleToUser("arnold", "ROLE_ADMIN");
			appUserService.addRoleToUser("arnold", "ROLE_USER");
		};
	}
}
