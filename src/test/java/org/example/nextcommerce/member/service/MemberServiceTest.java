package org.example.nextcommerce.member.service;

import org.example.nextcommerce.common.exception.BadRequestException;
import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.member.repository.jdbc.MemberJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @DisplayName("중복된 이메일이 존재하는 경우 BadRequestException")
    void isNotDuplicatedEmailExist(){
        //given
        when(memberJdbcRepository.findByEmail(any())).thenReturn(memberDto);
        //when-then
        assertThrows(BadRequestException.class, ()->{
            memberService.checkDuplicatedEmail(memberDto.getEmail());
        });
    }

    @Test
    @DisplayName("패스워드 유효성 검사 실패 BadRequestException")
    public void signUpPasswordValidFail(){
        MemberDto tmpDto = MemberDto.builder()
                .email("next@commerce.com")
                .password("123")
                .build();
        assertThrows(BadRequestException.class , ()->{
           memberService.isValidMemberDto(tmpDto);
        });
    }

    @Test
    @DisplayName("이메일 유효성 검사 실패 BadRequestException")
    public void signUpEmailValidFail(){
        MemberDto tmpDto = MemberDto.builder()
                .email("next@commerce")
                .password("1232asdf!")
                .build();
        assertThrows(BadRequestException.class, ()->{
            memberService.isValidMemberDto(tmpDto);
        });
    }

    @Test
    @DisplayName("회원가입 성공")
    @Transactional
    public void signUpSuccess(){
        //given
        memberService.create(memberDto);

        verify(memberJdbcRepository).save(memberDto);
    }



}
