package org.example.nextcommerce.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;

import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.post.dto.*;
import org.example.nextcommerce.post.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.ProductJdbcRepository;
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
    private final ImageFileService imageFileService;

    @Transactional
    public void save(List<ImageRequestDto> imageRequestDtoList, PostRequestDto postRequestDto, MemberDto memberDto){

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

        List<ImageDto> imageDtoList = imageFileService.parseImageFiles(imageRequestDtoList);
        imageJdbcRepository.saveAll(imageDtoList, postId);

    }

    public PostDto findPost(Long postId){
        return postJdbcRepository.findByPostId(postId);
    }

    @Transactional
    public void delete(Long postId, Long productId){
        List<ImageDto> imageDtoList = imageJdbcRepository.findAllByPostId(postId);
        imageFileService.deleteImageFiles(imageDtoList);
        imageJdbcRepository.deleteByPostId(postId);
        postJdbcRepository.deleteByPostId(postId);
        productJdbcRepository.deleteByProductId(productId);
    }

}
