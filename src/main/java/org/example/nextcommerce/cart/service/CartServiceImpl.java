package org.example.nextcommerce.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.repository.jdbc.CartJdbcRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements  CartService{

    private final CartJdbcRepository cartJdbcRepository;

    @Override
    public void save(Long memberId, CartRequestDto cartRequestDto) {
        CartDto cartDto = CartDto.builder()
                .memberId(memberId)
                .postId(cartRequestDto.getPostId())
                .quantity(cartRequestDto.getQuantity())
                .build();
        cartJdbcRepository.save(cartDto);
    }
}
