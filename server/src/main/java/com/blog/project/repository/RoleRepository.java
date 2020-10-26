package com.blog.project.repository;

import com.blog.project.model.ERoles;
import com.blog.project.model.RoleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleDao, Long> {
    Optional<RoleDao> findByName(ERoles name);
}