package org.example.nextcommerce.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nextcommerce.member.domain.dto.MemberDto;
import org.example.nextcommerce.member.repository.jpa.MemberJpaRepository;
import org.example.nextcommerce.member.service.MemberJpaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberApiController.class)
public class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private MemberDto memberDto;


    @MockBean
    private MemberJpaRepository memberJpaRepository;

    @MockBean
    private MemberJpaService memberJpaService;

    @MockBean
    private PasswordEncoder passwordEncoder;


    private MockHttpSession mockHttpSession;



    @BeforeEach
    void setUp() throws Exception {
        memberDto = MemberDto.builder()
                .email("nextcommerce@naver.com")
                .password("asdf123!")
                .build();

        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("MemberId", 1L);
        mockHttpSession.setAttribute("MemberDto",memberDto);
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void successCreatePost() throws Exception {

        String content = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(post("/api/members")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    public void successLogout() throws Exception{

        mockMvc.perform(get("/api/members/signout").session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(print());
        assertNull(mockHttpSession.getAttribute("MemberId"));

    }

    @Test
    @DisplayName("계정 삭제 성공 테스트")
    public void successDeleteMember() throws Exception{

        mockMvc.perform(delete("/api/members/account").session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(print());
        assertNull(mockHttpSession.getAttribute("MemberId"));

    }



}
