package org.example.nextcommerce.image.service;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.common.exception.NotFoundException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.image.entity.Image;
import org.example.nextcommerce.image.repository.jpa.ImageJpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageJpaService implements ImageService {

    private final ImageJpaRepository imageJpaRepository;
    private final ImageFileService imageFileService;

    @Override
    public byte[] getImageFileOne(Long imageId) {

        Image image = imageJpaRepository.findById(imageId).orElseThrow(()-> new NotFoundException(ErrorCode.ImagesNotFound));
        if(!imageFileService.validImageFile(image.getFilePath())){
            throw new NotFoundException(ErrorCode.ImageFileNotFound);
        }
        return imageFileService.downloadImageFile(image.getFilePath());
    }
}
