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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @LoginRequired
    @GetMapping(value = "/image/{postId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> imageFile(@PathVariable Long postId){

        byte[] bytes = cartService.getImageFileTop1(postId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    @LoginRequired
    @GetMapping()
    public ResponseEntity<List<CartDto>> cartListALl(@LoginMember MemberDto memberDto){

        List<CartDto> cartDtoList = cartService.getCartListAll(memberDto.getId());

        return ResponseEntity.status(HttpStatus.OK).body(cartDtoList);

    }




}
