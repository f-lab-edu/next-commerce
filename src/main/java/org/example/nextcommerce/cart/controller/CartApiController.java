package org.example.nextcommerce.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.dto.CartResponseDto;
import org.example.nextcommerce.cart.entity.Cart;
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
    @GetMapping()
    public ResponseEntity<List<CartDto>> cartListAll(@SessionAttribute Long MemberId){
        List<Cart> cartList = cartService.getCartListAll(MemberId);
        List<CartDto> cartDtoList = cartList.stream()
                .map(cart -> CartDto.builder()
                        .cartId(cart.getId())
                        .memberId(cart.getMember().getId())
                        .productId(cart.getProduct().getId())
                        .imageId(cart.getImage().getId())
                        .quantity(cart.getQuantity())
                        .build()).toList();
        return ResponseEntity.status(HttpStatus.OK).body(cartDtoList);

    }

    @LoginRequired
    @DeleteMapping("/{cartId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long cartId){
        cartService.delete(cartId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginRequired
    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAll(@LoginMember MemberDto memberDto){
        cartService.deleteAll(memberDto.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
