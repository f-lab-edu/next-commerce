package org.example.nextcommerce.image.service;

import org.example.nextcommerce.image.dto.ImageDto;
import org.example.nextcommerce.post.dto.ImageRequestDto;

import java.util.List;

public interface ImageFileService {

    public List<ImageDto> parseImageFiles(List<ImageRequestDto> imageRequestDtoList, Long postId);
    public void deleteImageFiles(List<ImageDto> imageDtoList);
    public void deleteDirectoryAll(String filePath);
    public byte[] downloadImageFile(String imagePath);
    public boolean validImageFile(String imagePath);


}
