package com.jwtsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jwtsecurity.domain.Role;
import com.jwtsecurity.domain.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);

    @Query("SELECT r.roleId FROM Role r WHERE r.roleName = :roleName")
    Long findIdByRoleName(@Param("roleName") RoleName roleName);
}