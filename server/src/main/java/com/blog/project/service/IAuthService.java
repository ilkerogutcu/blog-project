package com.blog.project.service;

import com.blog.project.payload.LoginRequest;
import com.blog.project.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public ResponseEntity<?> register(SignupRequest signUpRequest);

    public ResponseEntity<?> login(LoginRequest loginRequest);
}
