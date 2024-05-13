package org.example.nextcommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemeberApiController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MemberDto dto){
        if(memberService.isDuplicatedEmail(dto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        memberService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
