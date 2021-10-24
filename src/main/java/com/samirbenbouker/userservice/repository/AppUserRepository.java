package com.samirbenbouker.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samirbenbouker.userservice.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
}
