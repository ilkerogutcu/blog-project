package com.blog.project.dto;

import com.blog.project.model.TagDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailsDto {
    public Long id;
    public String title;
    public String subtitle;
    public String description;
    public String username;
    public String postImageUrl;
    public String postImageDescription;
    public Date createdDate;
    public List<TagDao> tags;

}
