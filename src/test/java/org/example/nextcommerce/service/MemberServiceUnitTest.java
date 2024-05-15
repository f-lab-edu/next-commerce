package org.example.nextcommerce.service;

import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.repository.jdbc.MemberJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
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
