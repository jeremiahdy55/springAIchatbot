package com.jwtsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtsecurity.domain.LoginDto;
import com.jwtsecurity.domain.User;
import com.jwtsecurity.security.jwt.JwtAuthResponse;
import com.jwtsecurity.service.AuthService;
import com.jwtsecurity.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    // Login user from existing data in DB
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok().body(jwtAuthResponse);
    }

    // Create new user and insert into DB
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginDto loginDto){
        User newUser = new User();
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userService.save(newUser, List.of("USER"));

        return ResponseEntity.ok().body(
            String.format("Saved user with username: \"%s\"", username));
    }

}