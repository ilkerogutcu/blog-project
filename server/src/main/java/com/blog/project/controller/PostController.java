package com.blog.project.controller;

import com.blog.project.dto.PostDetailsDto;
import com.blog.project.model.PostDao;
import com.blog.project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/get/posts")

    public ResponseEntity<List<PostDao>> gelAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping("/create/post/{name}")
    public ResponseEntity<?> createPost(@RequestBody PostDetailsDto postDetailsDto,@PathVariable String name) {
        postService.createPost(postDetailsDto, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/post/{id}")
    public PostDao getPostById(@PathVariable @RequestBody Long id) {
        return postService.getByPostId(id);
    }
}
