package com.blog.project.repository;

import com.blog.project.model.PostDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostDao, Long> {
}
