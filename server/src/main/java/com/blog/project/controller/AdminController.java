package com.blog.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    @RequestMapping(value = "/dashboard")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("hello admin");
    }

    @RequestMapping(value = "/hello")
    public ResponseEntity<String> helo() {
        return ResponseEntity.ok("hello world");
    }
}