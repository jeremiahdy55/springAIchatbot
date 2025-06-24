package com.jwtsecurity.domain;

import com.jwtsecurity.domain.enums.RoleName;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleId;
	
	@Enumerated(EnumType.STRING)
	private RoleName roleName;

    public Role() {}

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public long getRoleId() {
        return roleId;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
    
}
