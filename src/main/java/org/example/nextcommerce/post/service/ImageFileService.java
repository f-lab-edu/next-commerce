package org.example.nextcommerce.post.service;

import org.example.nextcommerce.post.dto.ImageDto;
import org.example.nextcommerce.post.dto.ImageRequestDto;

import java.util.List;

public interface ImageFileService {

    public List<ImageDto> parseImageFiles(List<ImageRequestDto> imageRequestDtoList);
    public void deleteImageFiles(List<ImageDto> imageDtoList);

}
