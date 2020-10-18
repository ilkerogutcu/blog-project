package com.blog.project.repository;

import com.blog.project.model.TagDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagDao, Long> {
}
