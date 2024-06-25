package org.example.nextcommerce.image.controller;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.image.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageApiController {
    private final ImageService imageService;

    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> imageFile(@PathVariable Long imageId){

        byte[] bytes = imageService.getImageFileOne(imageId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(bytes);
    }



}
