package org.example.nextcommerce.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.exception.FileHandleException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.dto.ImageDto;
import org.example.nextcommerce.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ImageFileHandler {
    public List<ImageDto> parseImageFiles(List<MultipartFile> multipartFileList){
        List<ImageDto> imageDtoList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentDate = now.format(dateTimeFormatter);

        Path directoryPath = Paths.get("src","main","resources","images", currentDate);
        //boolean isExist = Files.exists(directoryPath, LinkOption.NOFOLLOW_LINKS);
        try{
            Files.createDirectories(directoryPath);
        }
        catch (IOException e){
           throw new FileHandleException(e.getMessage());
        }

        for(MultipartFile multipartFile : multipartFileList){

            String originalFileExtension;
            String contentType = multipartFile.getContentType();

            if(contentType.contains(MediaType.IMAGE_JPEG_VALUE)){
                originalFileExtension = ".jpg";
            } else if (contentType.contains(MediaType.IMAGE_PNG_VALUE)) {
                originalFileExtension = ".png";
            }
            else{
                continue;
            }

            String imageName = System.nanoTime() + originalFileExtension;
            Path imagePath = Paths.get(directoryPath.toString(),imageName);
            ImageDto imageDto = ImageDto.builder()
                    .originalName(multipartFile.getOriginalFilename())
                    .filePath(imagePath.toString())
                    .fileSize(multipartFile.getSize())
                    .build();

            imageDtoList.add(imageDto);


            try {
                multipartFile.transferTo(imagePath);
            }catch (IOException e){
                throw new FileHandleException(e.getMessage());
            }
        }
        return imageDtoList;
    }



}
