package com.samirbenbouker.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samirbenbouker.userservice.domain.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long>{

}
