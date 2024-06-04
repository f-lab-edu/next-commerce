package org.example.nextcommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.dto.*;
import org.example.nextcommerce.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.repository.jdbc.ProductJdbcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;





@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostJdbcRepository postJdbcRepository;
    private final ImageJdbcRepository imageJdbcRepository;
    private final ProductJdbcRepository productJdbcRepository;

    @Transactional
    public void save(List<ImageDto> imageDtoList, PostRequestDto postRequestDto, MemberDto memberDto){

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

        imageJdbcRepository.saveAll(imageDtoList, postId);

    }

}
