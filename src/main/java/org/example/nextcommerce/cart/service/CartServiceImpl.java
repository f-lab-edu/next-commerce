package org.example.nextcommerce.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.entity.Cart;
import org.example.nextcommerce.cart.repository.jpa.CartJpaRepository;
import org.example.nextcommerce.common.exception.NotFoundException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.image.entity.Image;
import org.example.nextcommerce.image.repository.jpa.ImageJpaRepository;
import org.example.nextcommerce.member.domain.entity.Member;
import org.example.nextcommerce.member.repository.jpa.MemberJpaRepository;
import org.example.nextcommerce.post.entity.Product;
import org.example.nextcommerce.post.repository.jpa.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements  CartService{

    private final CartJpaRepository cartJpaRepository;
    private final ImageJpaRepository imageJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(Long memberId, CartRequestDto cartRequestDto) {

        Product product = productJpaRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(()-> new NotFoundException(ErrorCode.ProductsNotFound));

        Image image = imageJpaRepository.findByCreatedAtByPostId(cartRequestDto.getPostId())
                .orElseThrow(()->new NotFoundException(ErrorCode.ImagesNotFound));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(()->new NotFoundException(ErrorCode.MemberNotFound));

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .image(image)
                .quantity(cartRequestDto.getQuantity())
                .build();

        cartJpaRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCarts(Long memberId) {
        return cartJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public void delete(Long cartId) {
        cartJpaRepository.deleteById(cartId);
    }

    @Override
    @Transactional
    public void deleteAll(Long memberId) {
        cartJpaRepository.deleteAllByMemberId(memberId);
    }

}

