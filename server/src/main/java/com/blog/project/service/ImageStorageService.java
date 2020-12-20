package com.blog.project.service;

import com.blog.project.model.PostImageDao;
import com.blog.project.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ImageStorageService {

    private final ImageRepository imageRepository;

    public PostImageDao store(MultipartFile file) throws Exception {
        try{
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            PostImageDao image = new PostImageDao(fileName, file.getContentType(), file.getBytes());
            return imageRepository.save(image);
        }
        catch(Exception e){
            throw new Exception("Aynı resim isminde bir resim mevcut!");
        }


    }

    public PostImageDao getImage(String id) {
        return imageRepository.findById(id).get();
    }

    public Stream<PostImageDao> getAllFiles() {
        return imageRepository.findAll().stream();
    }

    public PostImageDao getImageByName(String name) {
        return imageRepository.findByName(name);
    }

}
