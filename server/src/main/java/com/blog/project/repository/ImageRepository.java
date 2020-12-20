package com.blog.project.repository;

import com.blog.project.model.PostImageDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface ImageRepository extends JpaRepository<PostImageDao, String> {
    PostImageDao findByName(String name);
}
