package org.example.nextcommerce.controller;

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
@RequestMapping(MemeberApiController.MEMBER_API_URI)
public class MemeberApiController {
    public static final String MEMBER_API_URI = "/api/members";

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody MemberDto dto){

        if(memberService.isDuplicatedEmail(dto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return (memberService.create(dto)) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
