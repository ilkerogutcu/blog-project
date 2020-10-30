package com.blog.project.service;

import com.blog.project.payload.LoginRequest;
import com.blog.project.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> register(SignupRequest signUpRequest);

    ResponseEntity<?> login(LoginRequest loginRequest);


}
