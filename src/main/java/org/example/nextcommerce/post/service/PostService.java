package org.example.nextcommerce.post.service;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.exception.NotFoundException;
import org.example.nextcommerce.common.exception.UnauthorizedException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.image.dto.ImageDto;
import org.example.nextcommerce.image.entity.Image;
import org.example.nextcommerce.image.repository.jpa.ImageJpaRepository;
import org.example.nextcommerce.image.service.ImageFileService;
import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.member.entity.Member;
import org.example.nextcommerce.member.repository.jpa.MemberJpaRepository;
import org.example.nextcommerce.post.dto.ImageRequestDto;
import org.example.nextcommerce.post.dto.PostDto;
import org.example.nextcommerce.post.dto.PostRequestDto;
import org.example.nextcommerce.post.dto.PostUpdateRequestDto;
import org.example.nextcommerce.post.entity.Post;
import org.example.nextcommerce.post.entity.Product;
import org.example.nextcommerce.post.repository.jpa.PostJpaRepository;
import org.example.nextcommerce.post.repository.jpa.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostJpaRepository postJpaRepository;
    private final ImageJpaRepository imageJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final ImageFileService imageFileService;
    private final MemberJpaRepository memberJpaRepository;


    @Transactional
    public void save(List<ImageRequestDto> imageRequestDtoList, PostRequestDto postRequestDto, MemberDto memberDto) {
        Product product = Product.builder()
                .code("code")
                .name(postRequestDto.getProductName())
                .price(postRequestDto.getProductPrice())
                .stock(postRequestDto.getProductStock())
                .build();
        Product saveProduct = productJpaRepository.save(product);

        Member member = memberJpaRepository.findById(memberDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.MemberNotFound));

        Post post = Post.builder()
                .member(member)
                .product(saveProduct)
                .content(postRequestDto.getPostContent())
                .title(postRequestDto.getPostTitle())
                .category(postRequestDto.getPostCategory())
                .build();
        Post savePost = postJpaRepository.save(post);

        List<ImageDto> imageDtoList = imageFileService.parseImageFiles(imageRequestDtoList, post.getId());
        List<Image> imageList = imageDtoList.stream()
                        .map(imageDto -> {
                            return Image.builder()
                                    .post(savePost)
                                    .originalName(imageDto.getOriginalName())
                                    .filePath(imageDto.getFilePath())
                                    .fileSize(imageDto.getFileSize())
                                    .build();
                        }).toList();
        imageJpaRepository.saveAll(imageList);

    }

    public Post findPost(Long postId) {
        return postJpaRepository.findById(postId).orElseThrow(()->new DatabaseException(ErrorCode.PostsNotFound));
    }

    @Transactional
    public void delete(Long postId, Long productId) {
        Image image = imageJpaRepository.findByCreatedAtByPostId(postId).orElseThrow(()->
                new DatabaseException(ErrorCode.ImagesNotFound));

        imageFileService.deleteDirectoryAll(image.getFilePath());

        imageJpaRepository.deleteAllByPostId(postId);

        postJpaRepository.deleteById(postId);
        productJpaRepository.deleteById(productId);
    }

    public void isPostAuthor(Long loginMemberId, Long postAuthorId) {
        if(!loginMemberId.equals(postAuthorId)){
            throw new UnauthorizedException(ErrorCode.PostsUnAuthorized);
        }
    }

    @Transactional
    public void update(Post post, PostUpdateRequestDto postUpdateRequestDto, List<ImageRequestDto> imageRequestDtoList) {

        Product product = productJpaRepository.findById(post.getProduct().getId())
                .orElseThrow(()->new DatabaseException(ErrorCode.ProductsNotFound));
        product.update(postUpdateRequestDto.getProductPrice());

        post.update(postUpdateRequestDto.getPostContent(), postUpdateRequestDto.getPostTitle());

        Optional.ofNullable(post.getProduct()).orElseThrow(
                ()->new NotFoundException(ErrorCode.ProductsNotFound)
        );

        if(!imageRequestDtoList.isEmpty() && imageRequestDtoList.get(0).getContentType() != null){
            Image image = imageJpaRepository.findByCreatedAtByPostId(post.getId())
                    .orElseThrow(()->new DatabaseException(ErrorCode.ImagesNotFound));
            imageFileService.deleteDirectoryAll(image.getFilePath());

            List<ImageDto> updatedImageDtoList = imageFileService.parseImageFiles(imageRequestDtoList, post.getId());
            imageJpaRepository.deleteAllByPostId(post.getId());

            List<Image> updatedImageList = updatedImageDtoList.stream()
                            .map(imageDto -> {
                                return Image.builder()
                                        .post(post)
                                        .originalName(imageDto.getOriginalName())
                                        .filePath(image.getFilePath())
                                        .fileSize(image.getFileSize())
                                        .build();
                            }).toList();
            imageJpaRepository.saveAll(updatedImageList);

        }



    }
}
