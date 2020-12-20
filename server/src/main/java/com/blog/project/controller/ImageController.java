package com.blog.project.controller;

import com.blog.project.dto.ImageResponse;
import com.blog.project.dto.ImageResponseMessage;
import com.blog.project.model.PostImageDao;
import com.blog.project.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.MultipartConfigElement;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api")
@CrossOrigin("*")
public class ImageController {

    @Autowired
    private ImageStorageService imageStorageService;

    @PostMapping(value="/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseMessage> uploadFile(@RequestPart(name = "uploadFile", required = false) MultipartFile file) {
        String message = "";
        try {
            imageStorageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ImageResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImageResponseMessage(message));
        }
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageResponse>> getListFiles() {
        List<ImageResponse> files = imageStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/image/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ImageResponse(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        PostImageDao image = imageStorageService.getImage(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .body(image.getData());
    }
}
