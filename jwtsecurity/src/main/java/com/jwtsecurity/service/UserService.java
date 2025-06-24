package com.jwtsecurity.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtsecurity.domain.Role;
import com.jwtsecurity.domain.User;
import com.jwtsecurity.domain.enums.RoleName;
import com.jwtsecurity.repository.RoleRepository;
import com.jwtsecurity.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public User save(User user, List<String> roles) {
        HashSet<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            Role r = roleRepository.findByRoleName(RoleName.valueOf(role))
                    .orElseThrow(() -> new RuntimeException("Role not found: " + role));
            roleSet.add(r);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRoles(roleSet);
        User userData = userRepository.save(user);
        return userData;
    }

    public long count() {
        return userRepository.count();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
