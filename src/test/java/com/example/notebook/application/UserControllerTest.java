package com.example.notebook.application;

import com.example.notebook.interfaces.request.LoginRequest;
import com.example.notebook.interfaces.request.SignupRequest;
import com.example.notebook.interfaces.response.LoginResponse;
import com.example.notebook.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void registerUser_createsUserSuccessfully() {
        SignupRequest signupRequest = new SignupRequest();
        doNothing().when(userService).registerUser(signupRequest);

        userController.registerUser(signupRequest);

        verify(userService, times(1)).registerUser(signupRequest);
    }

    @Test
    void loginUser_authenticatesAndReturnsToken() {
        LoginRequest loginRequest = new LoginRequest();
        LoginResponse loginResponse = new LoginResponse();
        when(userService.loginUser(loginRequest)).thenReturn(loginResponse);

        LoginResponse result = userController.loginUser(loginRequest);

        assertEquals(loginResponse, result);
    }

    @Test
    void registerUser_throwsExceptionWhenSignupRequestIsInvalid() {
        SignupRequest signupRequest = new SignupRequest();

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request")).when(userService).registerUser(signupRequest);

         assertThrows(ResponseStatusException.class, () -> {
            userController.registerUser(signupRequest);
        });

    }
}