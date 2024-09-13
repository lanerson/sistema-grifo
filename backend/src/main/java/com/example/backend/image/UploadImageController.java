package com.example.backend.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class UploadImageController {

    @Autowired
    private UploadImageService imageService;

    @PostMapping("/create")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            ImageUpload savedImage = imageService.saveImage(file);
            return new ResponseEntity<>("Image uploaded successfully: " + savedImage.getImageId(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID id) {
        ImageUpload image = imageService.getImage(id);

        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/png");
            return new ResponseEntity<>(image.getDataImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public List<ImageIdDTO> getAllImageIds() {
        return imageService.getAllImageIds().stream()
            .map(id -> {
                ImageIdDTO dto = new ImageIdDTO(id);
                dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UploadImageController.class).getImage(id)).withSelfRel());
                return dto;
            })
            .collect(Collectors.toList());
    }
}