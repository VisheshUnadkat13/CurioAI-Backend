package com.ai.ai_educator_platform.controller;

import com.ai.ai_educator_platform.dto.LoginRequest;
import com.ai.ai_educator_platform.dto.RegisterRequest;
import com.ai.ai_educator_platform.model.User;
import com.ai.ai_educator_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/public/login")

    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest)
    {
        return new ResponseEntity<>(userService.authenticateUser(loginRequest),HttpStatus.OK);
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest)
    {
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setRole("ROLE_USER");
        userService.registerUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

}
