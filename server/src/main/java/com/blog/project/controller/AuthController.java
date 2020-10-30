package com.blog.project.controller;

import com.blog.project.config.JwtTokenUtil;
import com.blog.project.model.UserDao;
import com.blog.project.payload.LoginRequest;
import com.blog.project.payload.SignupRequest;
import com.blog.project.repository.UserRepository;
import com.blog.project.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired(required = true)
    UserRepository userRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping
    @RequestMapping(value = "/register")
    public ResponseEntity<?> saveUser(@RequestBody SignupRequest signUpRequest) throws Exception {
        return authService.register(signUpRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/get/user/{username}")
    public Optional<UserDao> getUserByUsername(@PathVariable @RequestBody String username) {
        return userRepository.findByUsername(username);
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @GetMapping
    @RequestMapping(value = "/get/currentUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String getCurrentUser(Principal principal) {
        try {
            return principal.getName();
        } catch (NullPointerException e) {
            return "null";
        }

    }
}