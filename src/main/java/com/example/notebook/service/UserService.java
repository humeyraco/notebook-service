package com.example.notebook.service;

import com.example.notebook.entity.User;
import com.example.notebook.enums.UserRole;
import com.example.notebook.exception.CommonException;
import com.example.notebook.exception.ErrorCode;
import com.example.notebook.interfaces.dto.MyUserDetails;
import com.example.notebook.interfaces.request.LoginRequest;
import com.example.notebook.interfaces.request.SignupRequest;
import com.example.notebook.interfaces.response.LoginResponse;
import com.example.notebook.mapper.UserMapper;
import com.example.notebook.repository.UserRepository;
import com.example.notebook.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void registerUser(SignupRequest signupRequest) {
        userRepository.findByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail()).ifPresent(u -> {
            throw  CommonException.getCommonException(ErrorCode.USERNAME_OR_EMAIL_ALREADY_EXISTS);
        });
        User user = UserMapper.INSTANCE.mapToUser(signupRequest);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> CommonException.getCommonException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw CommonException.getCommonException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        }
        return new LoginResponse(jwtUtil.generateToken(user.getUsername()));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> CommonException.getCommonException(ErrorCode.USER_NOT_FOUND));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + UserRole.fromRoleId(user.getRole().getRoleId()).getRoleName()));
        return new MyUserDetails(user.getUsername(), user.getPassword(), authorities);
    }


    protected User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> CommonException.getCommonException(ErrorCode.USER_NOT_FOUND));
    }

    protected void checkIfUserSame(Long userId) {
        User user =  getAuthenticatedUser();
        if (!user.getId().equals(userId)) {
            throw CommonException.getCommonException(ErrorCode.USER_NOT_AUTHORIZED);
        }
    }

}