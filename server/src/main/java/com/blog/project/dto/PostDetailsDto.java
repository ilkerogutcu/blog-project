package com.blog.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailsDto {
    public Long id;
    public String title;
    public String description;
    public String username;

}
