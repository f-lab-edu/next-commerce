package org.example.nextcommerce.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.annotation.LoginMember;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.member.domain.dto.MemberDto;
import org.example.nextcommerce.member.service.MemberJpaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    //private final MemberJdbcService memberService;
    private final MemberJpaService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody MemberDto dto){
        memberService.isValidMemberDto(dto);

        memberService.checkDuplicatedEmail(dto.getEmail());

        dto.passwordCrypt(passwordEncoder);
        memberService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<HttpStatus> login(@RequestBody MemberDto dto, HttpSession httpSession){
        Long memberId = memberService.checkValidMember(dto.toEntity());
        httpSession.setAttribute("MemberId", memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequired
    @GetMapping("/session")
    public Long sessionTest(@SessionAttribute(name = "MemberId") Long memberId, @LoginMember MemberDto memberDto){
        log.info(memberDto.toString());
        return memberId;
    }

    @LoginRequired
    @GetMapping("/signout")
    public ResponseEntity<HttpStatus> logout(HttpSession httpSession){
        httpSession.removeAttribute("MemberId");
        httpSession.removeAttribute("MemberDto");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequired
    @DeleteMapping("/account")
    public ResponseEntity<HttpStatus> deleteMember(HttpSession httpSession){
        Long memberId = (Long) httpSession.getAttribute("MemberId");
        memberService.deleteMember(memberId);
        httpSession.removeAttribute("MemberId");
        httpSession.removeAttribute("MemberDto");
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
