package org.example.nextcommerce.post.service;

import org.example.nextcommerce.common.exception.UnauthorizedException;
import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.post.dto.*;
import org.example.nextcommerce.post.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.ProductJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private PostJdbcRepository postJdbcRepository;

    @MockBean
    private ImageJdbcRepository imageJdbcRepository;

    @MockBean
    private ProductJdbcRepository productJdbcRepository;

    @MockBean
    private ImageFileService imageFileService;

    private PostDto postDto;
    private ImageDto imageDto;
    private ImageRequestDto imageRequestDto;
    private ProductDto productDto;
    private PostRequestDto postRequestDto;
    private MemberDto memberDto;



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

        imageDto = ImageDto.builder()
                .imageId(1L)
                .postId(1L)
                .originalName(classPathResource.getFilename())
                .filePath(classPathResource.getPath())
                .fileSize(classPathResource.contentLength())
                .build();

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


    }

    @Test
    @DisplayName("게시물 작성자가 로그인한 사용자인지 확인 실패 테스트")
    public void failPostAuthor(){
        //given
        Long loginMemberId = 2L;

        //when-then
        assertThrows(UnauthorizedException.class, ()->{
            postService.isPostAuthor(loginMemberId, postDto.getMemberId());
        });
    }

    @Test
    @DisplayName("게시물 조회 성공 테스트")
    public void successFindPost(){
        //given
        when(postJdbcRepository.findByPostId(any())).thenReturn(postDto);

        //when
        PostDto findPostDto = postService.findPost(postDto.getPostId());

        //then
        assertThat(findPostDto).isNotNull();
        assertThat(findPostDto.getPostId()).isEqualTo(postDto.getPostId());
        assertThat(findPostDto.getProductId()).isEqualTo(postDto.getProductId());
    }

    @Test
    @DisplayName("게시물 삭제 성공 테스트")
    public void successDeletePost(){
        //given

        when(imageJdbcRepository.findOneByPostId(postDto.getPostId())).thenReturn(imageDto);
        /*
        doNothing().when(imageFileService).deleteDirectoryAll(imageDto.getFilePath());
        doNothing().when(imageJdbcRepository).deleteByPostId(postDto.getPostId());
        doNothing().when(postJdbcRepository).deleteByPostId(postDto.getPostId());
        doNothing().when(productJdbcRepository).deleteByProductId(postDto.getProductId());
         */

        //when
        postService.delete(postDto.getPostId(), postDto.getProductId());

        //then
        verify(imageJdbcRepository).findOneByPostId(anyLong());
        verify(imageFileService).deleteDirectoryAll(anyString());
        verify(imageJdbcRepository).deleteByPostId(anyLong());
        verify(postJdbcRepository).deleteByPostId(anyLong());
        verify(productJdbcRepository).deleteByProductId(anyLong());
    }

    @Test
    @DisplayName("게시물 저장 성공 테스트")
    public void successSavePost(){
        //given
        List<ImageDto> imageDtoList = new ArrayList<>();
        imageDtoList.add(imageDto);
        List<ImageRequestDto> imageRequestDtoList = new ArrayList<>();
        imageRequestDtoList.add(imageRequestDto);

        when(productJdbcRepository.save(any())).thenReturn(productDto.getProductId());
        when(postJdbcRepository.save(any())).thenReturn(postDto.getPostId());

        when(imageFileService.parseImageFiles(anyList(), anyLong())).thenReturn(imageDtoList);
        doNothing().when(imageJdbcRepository).saveAll(anyList(), anyLong());
        

        //when
        postService.save(imageRequestDtoList, postRequestDto, memberDto);

        //then
        assertThat(productJdbcRepository.save(productDto)).isNotNull();
        assertThat(productJdbcRepository.save(productDto)).isEqualTo(productDto.getProductId());
        assertThat(postJdbcRepository.save(postDto)).isNotNull();
        assertThat(postJdbcRepository.save(postDto)).isEqualTo(postDto.getPostId());
        assertThat(imageFileService.parseImageFiles(imageRequestDtoList, postDto.getPostId())).isNotNull();
        assertThat(imageFileService.parseImageFiles(imageRequestDtoList, postDto.getPostId())).isEqualTo(imageDtoList);

        verify(imageJdbcRepository).saveAll(anyList(), anyLong());
    }

    @Test
    @DisplayName("게시물 수정 성공 테스트")
    public void successUpdatePost(){
        //given
        doNothing().when(postJdbcRepository).update(any());
        doNothing().when(productJdbcRepository).update(any(), any());
        when(imageJdbcRepository.findOneByPostId(any())).thenReturn(imageDto);
        doNothing().when(imageFileService).deleteDirectoryAll(anyString());

        List<ImageDto> imageDtoList = new ArrayList<>();
        imageDtoList.add(imageDto);
        when(imageFileService.parseImageFiles(anyList(), any())).thenReturn(imageDtoList);

        doNothing().when(imageJdbcRepository).deleteByPostId(any());
        doNothing().when(imageJdbcRepository).saveAll(anyList(), any());

        //when
        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .postTitle("updateTitle")
                .postContent("updateContent")
                .productPrice("50000")
                .build();

        List<ImageRequestDto> imageRequestDtoList = new ArrayList<>();
        imageRequestDtoList.add(imageRequestDto);
        postService.update(postDto, postUpdateRequestDto, imageRequestDtoList );

        verify(postJdbcRepository).update(any());
        verify(productJdbcRepository).update(any(), any());

        assertThat(imageJdbcRepository.findOneByPostId(any())).isNotNull();
        assertThat(imageJdbcRepository.findOneByPostId(any())).isEqualTo(imageDto);

        verify(imageFileService).deleteDirectoryAll(anyString());

        assertThat(imageFileService.parseImageFiles(anyList(),any())).isNotNull();
        assertThat(imageFileService.parseImageFiles(anyList(),any())).isEqualTo(imageDtoList);

        verify(imageJdbcRepository).deleteByPostId(any());
        verify(imageJdbcRepository).saveAll(anyList(), any());


    }




}
