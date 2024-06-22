package org.example.nextcommerce.post.service;

import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.exception.UnauthorizedException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;

import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.post.dto.*;
import org.example.nextcommerce.post.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.ProductJdbcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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

        PostDto postDto = PostDto.builder()
                .memberId(memberDto.getId())
                .productId(productId)
                .content(postRequestDto.getPostContent())
                .category(postRequestDto.getPostCategory())
                .title(postRequestDto.getPostTitle())
                .build();

        Long postId = postJdbcRepository.save(postDto);

        List<ImageDto> imageDtoList = imageFileService.parseImageFiles(imageRequestDtoList, postId);
        imageJdbcRepository.saveAll(imageDtoList, postId);

    }

    public PostDto findPost(Long postId){
        return postJdbcRepository.findByPostId(postId);
    }

    @Transactional
    public void delete(Long postId, Long productId){
        ImageDto imageDto = imageJdbcRepository.findOneByPostId(postId);
        imageFileService.deleteDirectoryAll(imageDto.getFilePath());

        imageJdbcRepository.deleteByPostId(postId);
        postJdbcRepository.deleteByPostId(postId);
        productJdbcRepository.deleteByProductId(productId);
    }

    public void isPostAuthor(Long loginMemberId, Long postAuthorId){
        if(!loginMemberId.equals(postAuthorId)){
            throw new UnauthorizedException(ErrorCode.PostsUnAuthorized);
        }
    }

    @Transactional
    public void update(PostDto postDto, PostUpdateRequestDto postUpdateRequestDto, List<ImageRequestDto> imageRequestDtoList){
        postDto.updatePostDto(postUpdateRequestDto.getPostContent(), postUpdateRequestDto.getPostTitle());
        postJdbcRepository.update(postDto);
        productJdbcRepository.update(postDto.getProductId(), postUpdateRequestDto.getProductPrice());
        if(!imageRequestDtoList.isEmpty() && imageRequestDtoList.get(0).getContentType() != null){
            ImageDto imageDto = imageJdbcRepository.findOneByPostId(postDto.getPostId());
            imageFileService.deleteDirectoryAll(imageDto.getFilePath());

            List<ImageDto> updatedimageDtoList = imageFileService.parseImageFiles(imageRequestDtoList, postDto.getPostId());
            imageJdbcRepository.deleteByPostId(postDto.getPostId());
            imageJdbcRepository.saveAll(updatedimageDtoList, postDto.getPostId());
        }
    }

}
