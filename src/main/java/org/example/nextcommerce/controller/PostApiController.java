package org.example.nextcommerce.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.annotation.LoginMember;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.common.exception.FileHandleException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.dto.*;
import org.example.nextcommerce.service.ImageFileService;
import org.example.nextcommerce.service.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;
    private final ImageFileService imageFileService;

    @LoginRequired
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> createPost(@RequestPart List<MultipartFile> files, @RequestPart(value = "postRequestDto")PostRequestDto postRequestDto, @LoginMember MemberDto memberDto){

        if(files.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(postRequestDto == null || memberDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        List<ImageRequestDto> imageRequestDtoList = new ArrayList<>();
        for(MultipartFile file : files){
            ImageRequestDto imageRequestDto;
            try{
                imageRequestDto = ImageRequestDto.builder()
                        .imageInputStream(file.getInputStream())
                        .originalName(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .fileSize(file.getSize())
                        .build();
            }catch (IOException e){
                throw new FileHandleException(e.getMessage());
            }
            imageRequestDtoList.add(imageRequestDto);
        }

        List<ImageDto> imageDtoList = imageFileService.parseImageFiles(imageRequestDtoList);

        postService.save(imageDtoList, postRequestDto, memberDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
