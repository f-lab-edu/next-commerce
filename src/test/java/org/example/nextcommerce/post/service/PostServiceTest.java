package org.example.nextcommerce.post.service;

import org.example.nextcommerce.common.exception.UnauthorizedException;
import org.example.nextcommerce.image.dto.ImageDto;
import org.example.nextcommerce.image.entity.Image;
import org.example.nextcommerce.image.repository.jpa.ImageJpaRepository;
import org.example.nextcommerce.image.service.ImageFileService;
import org.example.nextcommerce.image.service.ImageJpaService;
import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.post.dto.*;
import org.example.nextcommerce.image.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.entity.Post;
import org.example.nextcommerce.post.entity.Product;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.ProductJdbcRepository;
import org.example.nextcommerce.post.repository.jpa.PostJpaRepository;
import org.example.nextcommerce.post.repository.jpa.ProductJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostJpaRepository postJpaRepository;

    @MockBean
    private ImageJpaRepository imageJpaRepository;

    @MockBean
    private ProductJpaRepository productJpaRepository;

    @MockBean
    private ImageFileService imageFileService;

    private PostDto postDto;
    private Image image;
    private ImageRequestDto imageRequestDto;
    private ProductDto productDto;
    private Product product;
    private PostRequestDto postRequestDto;
    private MemberDto memberDto;

    private Post post;


    @BeforeEach
    void setUp() throws IOException {
        postDto = PostDto.builder()
                .postId(1L)
                .title("title")
                .content("content")
                .productId(1L)
                .category("tech")
                .memberId(1L)
                .build();

        ClassPathResource classPathResource = new ClassPathResource("/test.jpg");


        imageRequestDto = ImageRequestDto.builder()
                .imageInputStream(classPathResource.getInputStream())
                .originalName(classPathResource.getFilename())
                .contentType(MediaType.IMAGE_JPEG_VALUE)
                .fileSize(classPathResource.contentLength())
                .build();

        productDto = ProductDto.builder()
                .productId(1L)
                .code("code")
                .name("이어폰")
                .price("30000")
                .stock(100)
                .build();

        postRequestDto = PostRequestDto.builder()
                .productName("이어폰")
                .productPrice("30000")
                .productStock(100)
                .postTitle("title")
                .postContent("content")
                .postCategory("tech")
                .build();

        memberDto = MemberDto.builder()
                .id(1L)
                .email("next@commerce.com")
                .password("asdf1234!")
                .build();

        product = productDto.toEntity();

        post = Post.builder()
                .id(1L)
                .member(memberDto.toEntity())
                .product(product)
                .title("title")
                .content("content")
                .category("tech")
                .build();

        image = Image.builder()
                .id(1L)
                .post(post)
                .originalName(classPathResource.getFilename())
                .filePath(classPathResource.getPath())
                .fileSize(classPathResource.contentLength())
                .build();

    }

    @Test
    @DisplayName("게시물 작성자가 로그인한 사용자인지 확인 실패 테스트")
    public void failPostAuthor(){
        //given
        Long loginMemberId = 2L;

        //when-then
        assertThrows(UnauthorizedException.class, ()->{
            postService.isPostAuthor(loginMemberId, post.getMember().getId());
        });
    }

    @Test
    @DisplayName("게시물 조회 성공 테스트")
    public void successFindPost(){
        //given
        when(postJpaRepository.findById(anyLong())).thenReturn(Optional.of(post));

        //when
        Post findPost = postService.findPost(post.getId());
        //then
        assertThat(findPost).isNotNull();
        assertThat(findPost.getId()).isEqualTo(post.getId());
        assertThat(findPost.getProduct().getId()).isEqualTo(postDto.getProductId());
    }

    @Test
    @DisplayName("게시물 삭제 성공 테스트")
    public void successDeletePost(){
        //given
        when(imageJpaRepository.findByCreatedAtByPostId(post.getId())).thenReturn(Optional.of(image));

        //when
        postService.delete(postDto.getPostId(), postDto.getProductId());

        //then
        verify(imageJpaRepository).findByCreatedAtByPostId(anyLong());
        //verify(imageFileService).deleteDirectoryAll(anyString());
        verify(imageJpaRepository).findByCreatedAtByPostId(anyLong());
        verify(postJpaRepository).deleteById(anyLong());
        verify(productJpaRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("게시물 저장 성공 테스트")
    public void successSavePost(){
        //given
        List<Image> imageList = new ArrayList<>();
        imageList.add(image);
        List<ImageRequestDto> imageRequestDtoList = new ArrayList<>();
        imageRequestDtoList.add(imageRequestDto);
        List<ImageDto> imageDtoList = imageList.stream()
                        .map(image1 -> {
                            return ImageDto.builder()
                                    .imageId(image1.getId())
                                    .postId(image1.getPost().getId())
                                    .originalName(image1.getOriginalName())
                                    .filePath(image1.getFilePath())
                                    .fileSize(image1.getFileSize())
                                    .build();
                        }).toList();

        when(productJpaRepository.save(any())).thenReturn(product);
        when(postJpaRepository.save(any())).thenReturn(post);
        when(imageFileService.parseImageFiles(anyList(), anyLong())).thenReturn(imageDtoList);
        when(imageJpaRepository.saveAll(imageList)).thenReturn(imageList);

        //when
        postService.save(imageRequestDtoList, postRequestDto, memberDto);

        //then
        assertThat(productJpaRepository.save(product)).isNotNull();
        assertThat(productJpaRepository.save(product)).isEqualTo(product);
        assertThat(postJpaRepository.save(post)).isNotNull();
        assertThat(postJpaRepository.save(post)).isEqualTo(post);
        assertThat(imageFileService.parseImageFiles(imageRequestDtoList, post.getId())).isNotNull();
        assertThat(imageFileService.parseImageFiles(imageRequestDtoList, post.getId())).isEqualTo(imageDtoList);

        verify(imageJpaRepository).saveAll(anyList());
    }


    @Test
    @DisplayName("게시물 수정 성공 테스트")
    public void successUpdatePost(){
        //given

        Post mockPost = mock(Post.class);
        Product mockProduct = mock(Product.class);

        when(productJpaRepository.findById(anyLong())).thenReturn(Optional.of(mockProduct));
        when(mockPost.getProduct()).thenReturn(mockProduct);
        when(imageJpaRepository.findByCreatedAtByPostId(anyLong())).thenReturn(Optional.of(image));
        //doNothing().when(imageFileService).deleteDirectoryAll(anyString());

        List<Image> imageList = new ArrayList<>();
        imageList.add(image);
        List<ImageDto> imageDtoList = imageList.stream()
                        .map(image1 -> {
                            return ImageDto.builder()
                                    .imageId(image1.getId())
                                    .postId(image1.getPost().getId())
                                    .originalName(image1.getOriginalName())
                                    .filePath(image1.getFilePath())
                                    .fileSize(image1.getFileSize())
                                    .build();
                        }).toList();
        when(imageFileService.parseImageFiles(anyList(), anyLong())).thenReturn(imageDtoList);

        doNothing().when(imageJpaRepository).deleteAllByPostId(anyLong());
        when(imageJpaRepository.saveAll(anyList())).thenReturn(imageList);

        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .postTitle("updateTitle")
                .postContent("updateContent")
                .productPrice("50000")
                .build();
        List<ImageRequestDto> imageRequestDtoList = new ArrayList<>();
        imageRequestDtoList.add(imageRequestDto);

        //when
        postService.update(mockPost, postUpdateRequestDto, imageRequestDtoList);

        //then
        verify(mockPost, times(1)).update(postUpdateRequestDto.getPostContent(), postUpdateRequestDto.getPostTitle());
        verify(mockProduct, times(1)).update(postUpdateRequestDto.getProductPrice());
        assertThat(imageJpaRepository.findByCreatedAtByPostId(anyLong())).isNotNull();
        assertThat(imageJpaRepository.findByCreatedAtByPostId(anyLong())).isEqualTo(Optional.of(image));
        //verify(imageFileService).deleteDirectoryAll(anyString());
        assertThat(imageFileService.parseImageFiles(anyList(),anyLong())).isNotNull();
        assertThat(imageFileService.parseImageFiles(anyList(),anyLong())).isEqualTo(imageDtoList);

        verify(imageJpaRepository).deleteAllByPostId(anyLong());
        assertThat(imageJpaRepository.saveAll(anyList())).isEqualTo(imageList);

    }


}
