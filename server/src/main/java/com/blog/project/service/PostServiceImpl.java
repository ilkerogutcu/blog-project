package com.blog.project.service;

import com.blog.project.config.JwtTokenUtil;
import com.blog.project.dto.PostDetailsDto;
import com.blog.project.extensions.PostNotFoundException;
import com.blog.project.model.PostDao;
import com.blog.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Transactional
    public List<PostDetailsDto> getAllPosts() {
        List<PostDao> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(PostDetailsDto postDto) {
        PostDao post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public PostDetailsDto getByPostId(Long id) {
        PostDao post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostDetailsDto mapFromPostToDto(PostDao post) {
        PostDetailsDto postDto = new PostDetailsDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setSubtitle(post.getSubtitle());
        postDto.setPostImageUrl(post.getPostImageUrl());
        postDto.setPostImageDescription(post.getPostImageDescription());
        postDto.setDescription(post.getDescription());
        postDto.setUsername(post.getUsername());
        postDto.setCreatedDate(post.getCreatedDate());
        postDto.setTags(post.getTags());
        return postDto;
    }

    private PostDao mapFromDtoToPost(PostDetailsDto postDto) {

        PostDao post = new PostDao();
        post.setTitle(postDto.getTitle());
        post.setSubtitle(postDto.getSubtitle());
        post.setDescription(postDto.getDescription());
        post.setPostImageUrl(postDto.getPostImageUrl());
        post.setPostImageDescription(postDto.getPostImageDescription());
        User loggedInUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        post.setCreatedDate(Date.from(Instant.now()));
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedDate(Date.from(Instant.now()));
        post.setTags(postDto.getTags());
        return post;
    }
}
