package com.jwtsecurity.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jwtsecurity.domain.Role;
import com.jwtsecurity.domain.enums.RoleName;
import com.jwtsecurity.repository.RoleRepository;

@Component
public class RoleDataInitializer {
    
    @Autowired
    RoleRepository roleRepository;

    public void init() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(RoleName.ADMIN));
            roleRepository.save(new Role(RoleName.USER));
        }
    }
}