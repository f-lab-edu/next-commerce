package org.example.nextcommerce.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.service.CartService;
import org.example.nextcommerce.common.annotation.LoginMember;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.member.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;

    @LoginRequired
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody CartRequestDto cartRequestDto, @LoginMember MemberDto memberDto){
        cartService.save(memberDto.getId(), cartRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
