package org.example.nextcommerce.cart.service;

import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;

import java.util.List;

public interface CartService {
    public void save(Long memberId,CartRequestDto cartRequestDto);
    public byte[] getImageFileTop1(Long postId);
    public List<CartDto> getCartListAll(Long memberId);
}
