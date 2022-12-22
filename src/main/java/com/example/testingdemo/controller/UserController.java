package com.example.testingdemo.controller;

import com.example.testingdemo.Types.UserFlow;
import com.example.testingdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserFlow.SignupRequest signupRequest) {
        UserFlow.Profile profile = userService.signup(signupRequest);
        System.out.println("Profile created with id : " + profile.id());
        return ResponseEntity.accepted().body("Profile created with id : " + profile.id());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserFlow.DefaultLogin loginRequest) {
        UserFlow.Authenticated login = userService.login(loginRequest);
        System.out.println("Authenticated: " + login.toString());
        return ResponseEntity.accepted().body(login.toString());
    }
}
