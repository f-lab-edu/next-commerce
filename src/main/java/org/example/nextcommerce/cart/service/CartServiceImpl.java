package org.example.nextcommerce.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.repository.jdbc.CartJdbcRepository;
import org.example.nextcommerce.image.dto.ImageDto;
import org.example.nextcommerce.image.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements  CartService{

    private final CartJdbcRepository cartJdbcRepository;
    private final ImageJdbcRepository imageJdbcRepository;
    private final PostJdbcRepository postJdbcRepository;

    @Override
    public void save(Long memberId, CartRequestDto cartRequestDto) {

        postJdbcRepository.findByPostId(cartRequestDto.getPostId());

        ImageDto imageDto = imageJdbcRepository.findRecentOneByPostId(cartRequestDto.getPostId());

        CartDto cartDto = CartDto.builder()
                .memberId(memberId)
                .postId(cartRequestDto.getPostId())
                .quantity(cartRequestDto.getQuantity())
                .imageId(imageDto.getImageId())
                .build();
        cartJdbcRepository.save(cartDto);
    }

    @Override
    public List<CartDto> getCartListAll(Long memberId) {
        return cartJdbcRepository.findAllByMemberId(memberId);
    }

    @Override
    public void delete(Long cartId) {
        cartJdbcRepository.deleteByCartId(cartId);
    }

    @Override
    public void deleteAll(Long memberId) {
        cartJdbcRepository.deleteAllByMemberId(memberId);
    }

}

