package org.example.nextcommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.service.SessionLoginService;
import org.example.nextcommerce.service.MemberService;
import org.example.nextcommerce.utils.annotation.LoginRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemeberApiController {

    private final MemberService memberService;
    private final SessionLoginService loginService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MemberDto dto){
        if(memberService.isDuplicatedEmail(dto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        memberService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid MemberDto dto){
        MemberDto dbDto = memberService.checkValidMember(dto);
        if( dbDto == null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        loginService.saveLoginMember(dbDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/session")
    @LoginRequired
    public Long sessionTest(){
        Long id = loginService.getSessionMemberId();
        log.info(Long.toString(id));
        return id;
    }



}
