package com.gpcf.expense_tracker_api.repository;

import com.gpcf.expense_tracker_api.entity.Role;
import com.gpcf.expense_tracker_api.enums.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
     Optional<Role> findByRoleName(AppRole roleAdmin);
}
