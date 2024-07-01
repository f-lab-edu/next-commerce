package org.example.nextcommerce.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.annotation.LoginMember;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.common.exception.FileHandleException;

import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.post.dto.ImageRequestDto;
import org.example.nextcommerce.post.dto.PostRequestDto;
import org.example.nextcommerce.post.dto.PostUpdateRequestDto;
import org.example.nextcommerce.post.entity.Post;

import org.example.nextcommerce.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostApiController {

    //private final PostJdbcService postService;
    private final PostService postService;

    @LoginRequired
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> createPost(@RequestPart List<MultipartFile> files, @RequestPart(value = "postRequestDto") PostRequestDto postRequestDto, @LoginMember MemberDto memberDto){

        if(files.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(postRequestDto == null || memberDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<ImageRequestDto> imageRequestDtoList = files.stream()
                .map(file -> {
                            try {
                                return ImageRequestDto.builder()
                                        .imageInputStream(file.getInputStream())
                                        .originalName(file.getOriginalFilename())
                                        .contentType(file.getContentType())
                                        .fileSize(file.getSize())
                                        .build();
                            } catch (IOException e) {
                                throw new FileHandleException(e.getMessage());
                            }
                        }
                ).toList();

        postService.save(imageRequestDtoList, postRequestDto, memberDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequired
    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId, @LoginMember MemberDto memberDto){

        Post post = postService.findPost(postId);
        postService.isPostAuthor(memberDto.getId(), post.getMember().getId());
        postService.delete(postId, post.getProduct().getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequired
    @PatchMapping("/{postId}")
    public ResponseEntity<HttpStatus> updatePost(@PathVariable Long postId, @RequestPart(required = false) List<MultipartFile> files, @RequestPart(value = "postUpdateRequestDto") PostUpdateRequestDto postUpdateRequestDto, @LoginMember MemberDto memberDto){

        Post post = postService.findPost(postId);
        postService.isPostAuthor(memberDto.getId(), post.getMember().getId());

        List<ImageRequestDto> imageRequestDtoList = new ArrayList<>();
        if(!files.isEmpty() && files.get(0).getContentType() != null){
            imageRequestDtoList = files.stream()
                    .map(file -> {
                                try {
                                    return ImageRequestDto.builder()
                                            .imageInputStream(file.getInputStream())
                                            .originalName(file.getOriginalFilename())
                                            .contentType(file.getContentType())
                                            .fileSize(file.getSize())
                                            .build();
                                } catch (IOException e) {
                                    throw new FileHandleException(e.getMessage());
                                }
                            }
                    )
                    .toList();
        }
        postService.update(post, postUpdateRequestDto ,imageRequestDtoList);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
