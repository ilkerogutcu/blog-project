package com.blog.project.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotNull
    private String title;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(nullable = false)
    @NotNull
    private String username;

    @Column(nullable = false)
    @NotNull
    private Instant createdDate;

    @Column(nullable = false)
    @NotNull
    private Instant updatedDate;
}
