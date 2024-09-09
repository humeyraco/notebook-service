package com.example.notebook.service;

import com.example.notebook.entity.User;
import com.example.notebook.enums.UserRole;
import com.example.notebook.exception.CommonException;
import com.example.notebook.exception.ErrorCode;
import com.example.notebook.interfaces.dto.MyUserDetails;
import com.example.notebook.interfaces.request.LoginRequest;
import com.example.notebook.interfaces.request.SignupRequest;
import com.example.notebook.interfaces.response.LoginResponse;
import com.example.notebook.repository.UserRepository;
import com.example.notebook.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_createsUserSuccessfully() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("username");
        signupRequest.setEmail("email@example.com");
        signupRequest.setPassword("password");
        signupRequest.setRoleId(UserRole.ADMIN.getRoleId());
        when(userRepository.findByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");

        userService.registerUser(signupRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_throwsExceptionWhenUsernameOrEmailExists() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("username");
        signupRequest.setEmail("email@example.com");

        when(userRepository.findByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail())).thenReturn(Optional.of(new User()));

        CommonException exception = assertThrows(CommonException.class, () -> {
            userService.registerUser(signupRequest);
        });

        assertEquals(ErrorCode.USERNAME_OR_EMAIL_ALREADY_EXISTS.getCode(), exception.getErrorCode());
    }

    @Test
    void loginUser_authenticatesAndReturnsToken() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn("token");

        LoginResponse response = userService.loginUser(loginRequest);

        assertEquals("token", response.getToken());
    }

    @Test
    void loginUser_throwsExceptionWhenUserNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());

        CommonException exception = assertThrows(CommonException.class, () -> {
            userService.loginUser(loginRequest);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND.getCode(), exception.getErrorCode());
    }

    @Test
    void loginUser_throwsExceptionWhenPasswordDoesNotMatch() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        CommonException exception = assertThrows(CommonException.class, () -> {
            userService.loginUser(loginRequest);
        });

        assertEquals(ErrorCode.PASSWORD_DOES_NOT_MATCH.getCode(), exception.getErrorCode());
    }

    @Test
    void listUsers_returnsListOfUsers() {
        List<User> users = List.of(new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.listUsers();

        assertEquals(users, result);
    }

    @Test
    void loadUserByUsername_returnsUserDetails() {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setRole(UserRole.ADMIN);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        MyUserDetails userDetails = (MyUserDetails) userService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_throwsExceptionWhenUserNotFound() {
        String username = "username";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        CommonException exception = assertThrows(CommonException.class, () -> {
            userService.loadUserByUsername(username);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND.getCode(), exception.getErrorCode());
    }
}