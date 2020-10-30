package com.blog.project.controller;

import com.blog.project.dto.PostDetailsDto;
import com.blog.project.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/get/posts")
    public ResponseEntity<List<PostDetailsDto>> gelAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping("/create/post")
    public ResponseEntity<?> createPost(@RequestBody PostDetailsDto postDetailsDto) {
        postService.createPost(postDetailsDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/post/{id}")
    public PostDetailsDto getPostById(@PathVariable @RequestBody Long id) {
        return postService.getByPostId(id);
    }
}
