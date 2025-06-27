package com.jwtsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtsecurity.domain.LoginDto;
import com.jwtsecurity.domain.User;
import com.jwtsecurity.security.jwt.JwtAuthResponse;
import com.jwtsecurity.security.jwt.JwtUtility;
import com.jwtsecurity.security.jwt.RevokedTokenService;
import com.jwtsecurity.service.AuthService;
import com.jwtsecurity.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    RevokedTokenService revokedTokenService;

    // Login user from existing data in DB
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok().body(jwtAuthResponse);
    }

    // Create new user and insert into DB
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginDto loginDto) {
        User newUser = new User();
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userService.save(newUser, List.of("USER"));

        return ResponseEntity.ok().body(
                String.format("Saved user with username: \"%s\"", username));
    }

    // Validate the token
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }
        
        String token = authHeader.substring(7);
        if (jwtUtility.validateToken(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // logout and revoke the token
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String jti = jwtUtility.getTokenId(token);
        revokedTokenService.revokeToken(jti);

        return ResponseEntity.ok("Logged out");
    }

}
