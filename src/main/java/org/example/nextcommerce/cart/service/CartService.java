package org.example.nextcommerce.cart.service;

import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;

import java.util.List;

public interface CartService {
    public void save(Long memberId,CartRequestDto cartRequestDto);
    public byte[] getImageFileOne(Long postId);
    public List<CartDto> getCartListAll(Long memberId);
    public void delete(Long cartId);
    public void deleteAll(Long memberId);

}
