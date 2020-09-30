package com.blog.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailsDto {
    public Long id;
    public String title;
    public String description;
    public String username;
    public String postImageUrl;
    public String postImageDescription;
    public Date createdDate;

}
