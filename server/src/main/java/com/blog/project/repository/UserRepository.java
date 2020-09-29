package com.blog.project.repository;


import com.blog.project.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserDao, Integer> {
    Optional<UserDao> findByUsername(String username);
}
