package com.example.notebook.interfaces.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Surname cannot be empty")
    private String surname;
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Role cannot be null")
    private Long roleId;
}
