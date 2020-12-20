package com.blog.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="tbl_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImageDao {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String type;
    @Lob
    private byte[] data;

    public PostImageDao(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
