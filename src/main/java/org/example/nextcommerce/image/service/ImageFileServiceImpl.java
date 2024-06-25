package org.example.nextcommerce.post.service;

import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.exception.FileHandleException;
import org.example.nextcommerce.image.dto.ImageDto;
import org.example.nextcommerce.post.dto.ImageRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.nio.file.Files.isDirectory;

@Slf4j
@Service
public class ImageFileServiceImpl implements ImageFileService {


    @Value("${nextcommerce.image.upload.location}")
    private String basePath;


    @Override
    public List<ImageDto> parseImageFiles(List<ImageRequestDto> imageRequestDtoList, Long postId){

        List<ImageDto> imageDtoList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentDate = now.format(dateTimeFormatter);
        Path directoryPath = Paths.get(basePath, currentDate, postId.toString());

        if(!isDirectory(directoryPath, LinkOption.NOFOLLOW_LINKS)){
            try{
                Files.createDirectories(directoryPath);
            }
            catch (IOException e){
                throw new FileHandleException(e.getMessage());
            }
        }

        for(ImageRequestDto imageRequestDto : imageRequestDtoList){

            String contentType = imageRequestDto.getContentType();
            String originalFileExtension;
            if(contentType.contains(MediaType.IMAGE_JPEG_VALUE)){
                originalFileExtension = ".jpg";
            } else if (contentType.contains(MediaType.IMAGE_PNG_VALUE)) {
                originalFileExtension = ".png";
            }
            else{
                continue;
            }

            String imageName = UUID.randomUUID() + originalFileExtension;
            Path imagePath = Paths.get(directoryPath.toString(),imageName);

            try{
                Files.copy(imageRequestDto.getImageInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                throw new FileHandleException(e.getMessage());
            }

            ImageDto imageDto = ImageDto.builder()
                    .originalName(imageRequestDto.getOriginalName())
                    .filePath(imagePath.toString())
                    .fileSize(imageRequestDto.getFileSize())
                    .build();

            imageDtoList.add(imageDto);
        }

        return imageDtoList;
    }

    @Override
    public void deleteImageFiles(List<ImageDto> imageDtoList) {
        for(ImageDto imageDto : imageDtoList){
            Path filePath = Paths.get(imageDto.getFilePath());
            log.info(filePath.toString());
            try{
                Files.deleteIfExists(filePath);
            }catch (IOException e){
                throw new FileHandleException(e.getMessage());
            }

        }

    }

    @Override
    public void deleteDirectoryAll(String imagePath) {
        Path filePath = Paths.get(imagePath).getParent();
        log.info(filePath.toString());
        try{
            Files.walk(filePath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }catch (IOException e){
            throw new FileHandleException(e.getMessage());
        }

    }

    @Override
    public byte[] downloadImageFile(String imagePath) {
        Path filePath = Paths.get(imagePath);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(filePath);
        }catch (Exception e){
            throw new FileHandleException(e.getMessage());
        }
        return bytes;
    }

    @Override
    public boolean validImageFile(String imagePath) {
        Path filePath = Paths.get(imagePath);
        if( Files.isDirectory(filePath) || Files.notExists(filePath, LinkOption.NOFOLLOW_LINKS) || !Files.isReadable(filePath) ){
            return false;
        }
        return true;
    }
}
