package org.example.nextcommerce.cart.service;

import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;

public interface CartService {
    public void save(Long memberId,CartRequestDto cartRequestDto);
}
