package org.example.nextcommerce.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.nextcommerce.common.resolver.LoginMemberArgumentResolver;

import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.member.repository.jdbc.MemberJdbcRepository;

import org.example.nextcommerce.post.dto.PostDto;
import org.example.nextcommerce.post.dto.PostRequestDto;

import org.example.nextcommerce.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.nio.charset.StandardCharsets;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostApiController.class)
public class PostApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberJdbcRepository memberJdbcRepository;

    @MockBean
    private PostService postService;

    @Autowired
    private PostApiController postApiController;

    private MockMvc mockMvc;
    private PostRequestDto postRequestDto;
    private MockHttpSession mockHttpSession;
    private MockManagerArgumentResolver mockManagerArgumentResolver = new MockManagerArgumentResolver();

    static class MockManagerArgumentResolver extends LoginMemberArgumentResolver {
        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

            MemberDto memberDto = MemberDto.builder()
                    .id(999L)
                    .email("test@naver.com")
                    .password("asdf7890*")
                    .build();
            return memberDto;
        }
    }

    @BeforeEach
    public void setup(){

        mockMvc = MockMvcBuilders.standaloneSetup(postApiController)
                .setControllerAdvice(new ExceptionHandlerExceptionResolver())
                .setCustomArgumentResolvers(mockManagerArgumentResolver)
                .build();

        MemberDto memberDto = MemberDto.builder()
                .id(999L)
                .email("test@naver.com")
                .password("asdf7890*")
                .build();
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("MemberId", 999L);
        mockHttpSession.setAttribute("MemberDto", memberDto);


        postRequestDto = PostRequestDto.builder()
                .productName("이어폰")
                .productPrice("30000")
                .productStock(200)
                .postTitle("애플 이어폰")
                .postContent("애플 신형 이어폰 판매")
                .postCategory("tech")
                .build();

    }

    @Test
    @DisplayName("게시물 생성 성공 테스트")
    public void successCreatePost() throws Exception{
        ClassPathResource classPathResource = new ClassPathResource("/test.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("files", "test.jpg", "image/jpeg",classPathResource.getInputStream());

        String postRequestDtoJson = objectMapper.writeValueAsString(postRequestDto);
        MockMultipartFile multipartFileDto = new MockMultipartFile("postRequestDto", "postRequestDto","application/json",postRequestDtoJson.getBytes(StandardCharsets.UTF_8));

        doNothing().when(postService).save(any(), any(), any());

        mockMvc.perform(multipart("/api/posts").file(multipartFile).file(multipartFileDto).session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("게시물 삭제 성공 테스트")
    public void successDeletePost() throws Exception{

        PostDto postDto = PostDto.builder()
                .postId(1L)
                .memberId(1L)
                .productId(1L)
                .title("애플 이어폰")
                .content("애플 신형 이어폰 판매")
                .category("tech")
                .build();

        given(postService.findPost(any())).willReturn(postDto);
        doNothing().when(postService).isPostAuthor(any(), any());
        doNothing().when(postService).delete(any(), any());
        mockMvc.perform(delete("/api/posts/1").contentType(MediaType.APPLICATION_JSON).session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(print());
    }




}
