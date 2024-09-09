package com.example.notebook.application;

import com.example.notebook.interfaces.request.LoginRequest;
import com.example.notebook.interfaces.request.SignupRequest;
import com.example.notebook.interfaces.response.LoginResponse;
import com.example.notebook.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid SignupRequest signupRequest) {
        userService.registerUser(signupRequest);
    }

    @GetMapping("/login")
    public LoginResponse loginUser(@RequestBody @Valid LoginRequest user) {
        return userService.loginUser(user);
    }

}