package org.example.nextcommerce.member.service;

import org.example.nextcommerce.common.exception.BadRequestException;
import org.example.nextcommerce.member.domain.dto.MemberDto;
import org.example.nextcommerce.member.domain.entity.Member;
import org.example.nextcommerce.member.repository.jpa.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {

    @InjectMocks
    private MemberJpaService memberService;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    private MemberDto memberDto;
    private Member member;

    @BeforeEach
    void setUp(){
        memberDto = MemberDto.builder()
                .email("nextcommerce@naver.com")
                .password("asdf123!")
                .build();
        member = memberDto.toEntity();
    }

    @Test
    @DisplayName("중복된 이메일이 존재하는 경우 BadRequestException")
    void isDuplicatedEmailExistFail(){
        //given
        when(memberJpaRepository.findMemberByEmail(any())).thenReturn(Optional.of(member));
        //when-then
        assertThrows(BadRequestException.class,()->{
            memberService.checkDuplicatedEmail(memberDto.getEmail());
        });
    }

    @Test
    @DisplayName("현재 Member 객체에 대한 Email, Password 유호성 검사 성공")
    void isValidMemberDtoTest(){
        assertTrue(memberService.isValidMemberDto(memberDto));
    }

}
