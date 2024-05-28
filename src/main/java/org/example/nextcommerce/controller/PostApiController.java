package org.example.nextcommerce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.annotation.LoginMember;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.dto.PostDto;
import org.example.nextcommerce.dto.PostRequestDto;
import org.example.nextcommerce.service.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;

    @LoginRequired
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> createPost(@RequestPart List<MultipartFile> files, @RequestPart(value = "postRequestDto")PostRequestDto postRequestDto, @LoginMember MemberDto memberDto){

        if(files.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(postRequestDto == null || memberDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        return ResponseEntity.status(HttpStatus.OK).build();
    }




    @PostMapping(path = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> imageTest(@RequestPart List<MultipartFile> files){

        if(files.isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        log.info(files.get(0).toString());
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
