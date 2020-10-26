package com.blog.project.payload;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Set<String> role;

}
