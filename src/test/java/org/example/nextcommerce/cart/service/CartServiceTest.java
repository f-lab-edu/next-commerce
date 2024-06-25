package org.example.nextcommerce.cart.service;

import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.repository.jdbc.CartJdbcRepository;

import org.example.nextcommerce.post.dto.ImageDto;
import org.example.nextcommerce.post.dto.PostDto;
import org.example.nextcommerce.post.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.post.service.ImageFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private CartJdbcRepository cartJdbcRepository;

    @MockBean
    private PostJdbcRepository postJdbcRepository;

    @MockBean
    private ImageJdbcRepository imageJdbcRepository;

    @MockBean
    private ImageFileService imageFileService;

    private PostDto postDto;
    private CartRequestDto cartRequestDto;

    private CartDto cartDto;
    private ImageDto imageDto;

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

        cartRequestDto = CartRequestDto.builder()
                .postId(postDto.getPostId())
                .quantity(2)
                .build();

        cartDto = CartDto.builder()
                .memberId(1L)
                .postId(cartRequestDto.getPostId())
                .quantity(cartRequestDto.getQuantity())
                .imageId(1L)
                .build();

        ClassPathResource classPathResource = new ClassPathResource("/test.jpg");

        imageDto = ImageDto.builder()
                .imageId(1L)
                .postId(1L)
                .originalName(classPathResource.getFilename())
                .filePath(classPathResource.getPath())
                .fileSize(classPathResource.contentLength())
                .build();
    }

    @Test
    @DisplayName("장바구니 저장 성공 테스트")
    public void successSaveCart(){
        //given
        Long memberId = 999L;
        when(imageJdbcRepository.findRecentOneByPostId(any())).thenReturn(imageDto);
        doNothing().when(cartJdbcRepository).save(any());

        //when
        cartService.save(memberId, cartRequestDto);

        //then
        verify(cartJdbcRepository).save(any());
    }




}
