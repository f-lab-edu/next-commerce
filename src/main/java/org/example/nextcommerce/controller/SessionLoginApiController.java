package org.example.nextcommerce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.common.utils.SessionUtils;
import org.example.nextcommerce.dto.MemberDto;

import org.example.nextcommerce.service.MemberService;
import org.example.nextcommerce.service.SessionLoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SessionLoginApiController {

    private final SessionLoginService loginService;
    private final MemberService memberService;

    @PostMapping("/signin")
    public ResponseEntity<HttpStatus> login(@RequestBody MemberDto dto){
        MemberDto dbDto = memberService.checkValidMember(dto);
        loginService.loginMember(dbDto.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/session")
    @LoginRequired
    public Long sessionTest(){
        return SessionUtils.getLoginSessionMemberId();
    }

}
