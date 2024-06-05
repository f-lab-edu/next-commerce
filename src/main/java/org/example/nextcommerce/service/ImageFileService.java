package org.example.nextcommerce.service;

import org.example.nextcommerce.dto.ImageDto;
import org.example.nextcommerce.dto.ImageRequestDto;

import java.util.List;

public interface ImageFileService {

    public List<ImageDto> parseImageFiles(List<ImageRequestDto> imageRequestDtoList);
    public void deleteImageFiles(List<ImageDto> imageDtoList);

}
