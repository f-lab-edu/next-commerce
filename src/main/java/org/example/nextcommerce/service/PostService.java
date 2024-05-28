package org.example.nextcommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.config.ImageFileHandler;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.dto.*;
import org.example.nextcommerce.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.repository.jdbc.ProductJdbcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;





@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostJdbcRepository postJdbcRepository;
    private final ImageFileHandler imageFileHandler;
    private final ImageJdbcRepository imageJdbcRepository;
    private final ProductJdbcRepository productJdbcRepository;

    @Transactional
    public void save(List<MultipartFile> files, PostRequestDto postRequestDto, MemberDto memberDto){

        ProductDto productDto = ProductDto.builder()
                                .code("code")
                                .name(postRequestDto.getProductName())
                                .price(postRequestDto.getProductPrice())
                                .stock(postRequestDto.getProductStock())
                                .build();

        Long productId = productJdbcRepository.save(productDto);
        if( productId == null){
            throw new DatabaseException(ErrorCode.DBInsertFail);
        }

        PostDto postDto = PostDto.builder()
                .memberId(memberDto.getId())
                .productId(productId)
                .content(postRequestDto.getPostContent())
                .category(postRequestDto.getPostCategory())
                .title(postRequestDto.getPostTitle())
                .build();

        Long postId = postJdbcRepository.save(postDto);
        if(postId == null){
            throw new DatabaseException(ErrorCode.DBInsertFail);
        }


        List<ImageDto> imageDtoList = imageFileHandler.parseImageFiles(files);
        imageJdbcRepository.saveAll(imageDtoList, postId);
        //postJdbcRepository.savePost();

    }

    public void fileHandlerTest(List<MultipartFile> multipartFileList){

        List<ImageDto> imageDtoList = imageFileHandler.parseImageFiles(multipartFileList);
        log.info(imageDtoList.toString());
    }



}
