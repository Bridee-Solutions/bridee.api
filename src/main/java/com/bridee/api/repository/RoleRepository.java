package com.bridee.api.repository;

import com.bridee.api.entity.Role;
import com.bridee.api.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
