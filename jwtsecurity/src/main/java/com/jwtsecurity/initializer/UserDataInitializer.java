package com.jwtsecurity.initializer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jwtsecurity.domain.User;
import com.jwtsecurity.service.UserService;

@Component
public class UserDataInitializer {
    
    @Autowired
    UserService userService;

    public void init() {
        if (userService.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            userService.save(user, List.of("ADMIN", "USER"));
        }
    }
}