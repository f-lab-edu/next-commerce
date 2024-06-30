package org.example.nextcommerce.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.entity.Cart;
import org.example.nextcommerce.cart.repository.jdbc.CartJdbcRepository;
import org.example.nextcommerce.cart.repository.jpa.CartJpaRepository;
import org.example.nextcommerce.cart.service.CartService;
import org.example.nextcommerce.common.resolver.LoginMemberArgumentResolver;
import org.example.nextcommerce.image.repository.jpa.ImageJpaRepository;
import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.member.repository.jdbc.MemberJdbcRepository;
import org.example.nextcommerce.member.repository.jpa.MemberJpaRepository;
import org.example.nextcommerce.member.service.MemberJdbcService;
import org.example.nextcommerce.image.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.member.service.MemberJpaService;
import org.example.nextcommerce.post.controller.PostApiControllerTest;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.image.service.ImageFileService;
import org.example.nextcommerce.post.repository.jpa.PostJpaRepository;
import org.example.nextcommerce.post.repository.jpa.ProductJpaRepository;
import org.example.nextcommerce.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartApiController.class)
public class CartApiControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private MemberJpaService memberJpaService;
    @MockBean
    private MemberJpaRepository memberJpaRepository;

    @MockBean
    private PostService postService;

    @MockBean
    private CartJpaRepository cartJpaRepository;

    @MockBean
    private ImageFileService imageFileService;

    @MockBean
    private ImageJpaRepository imageJpaRepository;

    @MockBean
    private ProductJpaRepository productJpaRepository;


    @MockBean
    CartService cartService;

    private CartRequestDto cartRequestDto;

    private MockHttpSession mockHttpSession;

    private MockMvc mockMvc;

    @Autowired
    private CartApiController cartApiController;

    private MockManagerArgumentResolver mockManagerArgumentResolver = new MockManagerArgumentResolver();

    static class MockManagerArgumentResolver extends LoginMemberArgumentResolver{
        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
            MemberDto memberDto = MemberDto.builder()
                    .id(1L)
                    .email("test@naver.com")
                    .password("asdf7890*")
                    .build();
            return memberDto;
        }
    }


    @BeforeEach
    void setUp(){

        mockMvc = MockMvcBuilders.standaloneSetup(cartApiController)
                .setControllerAdvice(new ExceptionHandlerExceptionResolver())
                .setCustomArgumentResolvers(mockManagerArgumentResolver)
                .build();

        MemberDto memberDto = MemberDto.builder()
                        .id(1L)
                        .email("test@naver.com")
                        .password("asdf7890*")
                        .build();

        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("MemberId", 1L);
        mockHttpSession.setAttribute("MemberDto", memberDto);

        cartRequestDto = CartRequestDto.builder()
                .postId(1L)
                .quantity(10)
                .build();
    }

    @DisplayName("장바구니 추가 성공 테스트")
    @Test
    public void successCreateCart() throws Exception{

        doNothing().when(cartService).save(any(), any());

        String content = objectMapper.writeValueAsString(cartRequestDto);
        mockMvc.perform(post("/api/cart")
                        .session(mockHttpSession)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("장바구니 조회 성공 테스트")
    @Test
    public void successCartListAll() throws Exception{
        //given
        given(cartService.getCartListAll(anyLong())).willReturn(List.of());

        //then
        mockMvc.perform(get("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("장바구니 삭제 성공 테스트")
    @Test
    public void successCartDelete() throws Exception{
        doNothing().when(cartService).delete(any());
        mockMvc.perform(delete("/api/cart/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("멤버의 장바구니 전체 삭제 성공 테스트")
    @Test
    public void successDeleteAllByMemberId() throws Exception{
        doNothing().when(cartService).deleteAll(any());
        mockMvc.perform(delete("/api/cart").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }




}
