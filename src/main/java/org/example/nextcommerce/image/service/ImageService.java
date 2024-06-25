package org.example.nextcommerce.image.service;

import lombok.RequiredArgsConstructor;

import org.example.nextcommerce.common.exception.NotFoundException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.image.dto.ImageDto;
import org.example.nextcommerce.image.repository.jdbc.ImageJdbcRepository;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageJdbcRepository imageJdbcRepository;
    private final ImageFileService imageFileService;

    public byte[] getImageFileOne(Long imageId){
        ImageDto imageDto = imageJdbcRepository.findByImageId(imageId);
        if(!imageFileService.validImageFile(imageDto.getFilePath())){
            throw new NotFoundException(ErrorCode.ImageFileNotFound);
        }
        return imageFileService.downloadImageFile(imageDto.getFilePath());
    }



}
