package com.carloslaurinedev.medicalregisterapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carloslaurinedev.medicalregisterapi.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
