package org.example.nextcommerce.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.repository.jdbc.CartJdbcRepository;
import org.example.nextcommerce.cart.service.CartService;
import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.member.repository.jdbc.MemberJdbcRepository;
import org.example.nextcommerce.member.service.MemberService;
import org.example.nextcommerce.image.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.image.service.ImageFileService;
import org.example.nextcommerce.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartApiController.class)
public class CartApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private MemberService memberService;

    @MockBean
    private PostService postService;

    @MockBean
    private MemberJdbcRepository memberJdbcRepository;

    @MockBean
    private CartJdbcRepository cartJdbcRepository;

    @MockBean
    private ImageFileService imageFileService;

    @MockBean
    private ImageJdbcRepository imageJdbcRepository;

    @MockBean
    private PostJdbcRepository postJdbcRepository;


    @MockBean
    CartService cartService;

    private CartRequestDto cartRequestDto;

    private MockHttpSession mockHttpSession;


    @BeforeEach
    void setUp(){
        MemberDto memberDto = MemberDto.builder()
                        .id(999L)
                        .email("test@naver.com")
                        .password("asdf7890*")
                        .build();

        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("MemberId", 999L);
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
        given(cartService.getCartListAll(any())).willReturn(List.of());
        mockMvc.perform(get("/api/cart").contentType(MediaType.APPLICATION_JSON))
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
