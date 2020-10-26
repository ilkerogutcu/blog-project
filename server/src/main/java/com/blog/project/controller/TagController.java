package com.blog.project.controller;

import com.blog.project.model.TagDao;
import com.blog.project.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/get/tags")
    public ResponseEntity<List<TagDao>> getAllTags() {
        return new ResponseEntity<>(this.tagRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create/tag")
    public ResponseEntity<?> createTag(@RequestBody TagDao tagDao) {
        this.tagRepository.save(tagDao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/tag/{id}")
    public Optional<TagDao> getTagById(@PathVariable @RequestBody Long id) {
        return this.tagRepository.findById(id);
    }
}
