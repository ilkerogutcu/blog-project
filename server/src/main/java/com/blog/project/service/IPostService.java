package com.blog.project.service;


import com.blog.project.dto.PostDetailsDto;

import java.util.List;

public interface IPostService {
    List<PostDetailsDto> getAllPosts();

    void createPost(PostDetailsDto postDto);

    PostDetailsDto getByPostId(Long id);
    
}
