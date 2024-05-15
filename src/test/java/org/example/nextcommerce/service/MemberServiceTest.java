package org.example.nextcommerce.service;

import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.repository.jdbc.MemberJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberJdbcRepository memberJdbcRepository;

    private MemberDto memberDto;

    @BeforeEach
    void setUp(){
        memberDto = MemberDto.builder()
                .email("nextcommerce@naver.com")
                .password("asdf123!")
                .build();

    }

    @Test
    @DisplayName("중복된 이메일이 존재하지 않는 경우 False를 반환")
    void isNotDuplicatedEmailExist(){
        //given
        when(memberJdbcRepository.findByEmail(any())).thenReturn(null);
        //when-then
        assertFalse(memberService.isDuplicatedEmail(memberDto.getEmail()));
    }

    @Test
    @DisplayName("중복된 이메일이 존재하는 경우 True를 반환")
    void isDuplicatedEmailExist(){
        //given
        when(memberJdbcRepository.findByEmail(any())).thenReturn(memberDto);
        //when-then
        assertTrue(memberService.isDuplicatedEmail(memberDto.getEmail()));
    }

}
