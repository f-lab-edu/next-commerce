package org.example.nextcommerce.cart.service;

import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.entity.Cart;
import org.example.nextcommerce.cart.repository.jdbc.CartJdbcRepository;

import org.example.nextcommerce.cart.repository.jpa.CartJpaRepository;
import org.example.nextcommerce.image.dto.ImageDto;
import org.example.nextcommerce.image.entity.Image;
import org.example.nextcommerce.image.repository.jpa.ImageJpaRepository;
import org.example.nextcommerce.member.entity.Member;
import org.example.nextcommerce.member.repository.jpa.MemberJpaRepository;
import org.example.nextcommerce.member.service.MemberJpaService;
import org.example.nextcommerce.post.dto.PostDto;
import org.example.nextcommerce.image.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.entity.Post;
import org.example.nextcommerce.post.entity.Product;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.image.service.ImageFileService;
import org.example.nextcommerce.post.repository.jpa.ProductJpaRepository;
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

import java.io.IOException;
import java.util.Optional;

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
    private CartJpaRepository cartJpaRepository;

    @MockBean
    private ImageJpaRepository imageJpaRepository;

    @MockBean
    private ProductJpaRepository productJpaRepository;

    @MockBean
    private MemberJpaRepository memberJpaRepository;

    @MockBean
    private ImageFileService imageFileService;

    private Post post;
    private CartRequestDto cartRequestDto;

    private Cart cart;
    private Image image;
    private Member member;
    private Product product;

    @BeforeEach
    void setUp() throws IOException {
        product = Product.builder()
                .name("name")
                .code("code")
                .price("10000")
                .stock(500)
                .build();

        member = Member.builder()
                .id(1L)
                .email("test@naver.com")
                .password("abcd1234!")
                .build();

        post = Post.builder()
                .id(1L)
                .title("title")
                .content("content")
                .product(product)
                .category("tech")
                .member(member)
                .build();

        cartRequestDto = CartRequestDto.builder()
                .postId(1L)
                .productId(1L)
                .quantity(2)
                .build();

        cart = Cart.builder()
                .id(1L)
                .product(product)
                .quantity(cartRequestDto.getQuantity())
                .image(image)
                .build();

        ClassPathResource classPathResource = new ClassPathResource("/test.jpg");

        image = Image.builder()
                .id(1L)
                .post(post)
                .originalName(classPathResource.getFilename())
                .filePath(classPathResource.getPath())
                .fileSize(classPathResource.contentLength())
                .build();
    }

    @Test
    @DisplayName("장바구니 저장 성공 테스트")
    public void successSaveCart(){
        //given
        when(productJpaRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(imageJpaRepository.findByCreatedAtByPostId(anyLong())).thenReturn(Optional.of(image));
        when(memberJpaRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(cartJpaRepository.save(cart)).thenReturn(cart);

        //when
        cartService.save(member.getId(), cartRequestDto);

        //then
        verify(productJpaRepository).findById(anyLong());
        verify(imageJpaRepository).findByCreatedAtByPostId(anyLong());
        verify(memberJpaRepository).findById(anyLong());
        verify(cartJpaRepository).save(any());
    }




}
