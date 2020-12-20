package com.blog.project.service;

import com.blog.project.config.JwtTokenUtil;
import com.blog.project.dto.PostDetailsDto;
import com.blog.project.extensions.PostNotFoundException;
import com.blog.project.model.PostDao;
import com.blog.project.repository.ImageRepository;
import com.blog.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Transactional
    public List<PostDao> getAllPosts() {
        List<PostDao> posts = postRepository.findAll();
        return posts;
    }

    public void createPost(PostDetailsDto postDto, String name) {
        PostDao post = mapFromDtoToPost(postDto, name);
        postRepository.save(post);
    }

    public PostDao getByPostId(Long id) {
        PostDao post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return post;
    }

    private PostDetailsDto mapFromPostToDto(PostDao post, String name) {
        PostDetailsDto postDto = new PostDetailsDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setSubtitle(post.getSubtitle());
        postDto.setImageUrl(imageStorageService.getImageByName(name).getId());
        postDto.setPostImageDescription("selam dünya");
        postDto.setDescription(post.getDescription());
        postDto.setUsername("admin");
        postDto.setCreatedDate(post.getCreatedDate());
        postDto.setTags(post.getTags());
        return postDto;
    }

    private PostDao mapFromDtoToPost(PostDetailsDto postDto, String name) {

        PostDao post = new PostDao();
        post.setTitle(postDto.getTitle());
        post.setSubtitle(postDto.getSubtitle());
        post.setDescription(postDto.getDescription());
        post.setImageUrl(imageStorageService.getImageByName(name).getId());
        post.setPostImageDescription("selam dünya resmi");
        post.setCreatedDate(Date.from(Instant.now()));
        post.setUsername("admin");
        post.setUpdatedDate(Date.from(Instant.now()));
        post.setTags(postDto.getTags());
        return post;
    }
}
