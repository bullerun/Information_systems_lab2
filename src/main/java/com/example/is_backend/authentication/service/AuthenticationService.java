package com.example.is_backend.authentication.service;

import com.example.is_backend.authentication.requsts.SignInRequest;
import com.example.is_backend.authentication.requsts.SignUpRequest;
import com.example.is_backend.entity.User;

import com.example.is_backend.authentication.responses.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserServices userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) throws IllegalArgumentException {
        var user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).build();
        user = userService.create(user);
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(user.getId(), user.getUsername(), jwt, user.getRole());
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password.");
        }
        var userDetails = userService.getByUsername(request.getUsername());
        var jwt = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(userDetails.getId(), userDetails.getUsername(), jwt, userDetails.getRole());
    }
}
