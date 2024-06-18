package org.example.nextcommerce.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.cart.dto.CartRequestDto;
import org.example.nextcommerce.cart.repository.jdbc.CartJdbcRepository;
import org.example.nextcommerce.common.exception.NotFoundException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.post.dto.ImageDto;
import org.example.nextcommerce.post.dto.PostDto;
import org.example.nextcommerce.post.repository.jdbc.ImageJdbcRepository;
import org.example.nextcommerce.post.repository.jdbc.PostJdbcRepository;
import org.example.nextcommerce.post.service.ImageFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements  CartService{

    private final CartJdbcRepository cartJdbcRepository;
    private final ImageFileService imageFileService;
    private final ImageJdbcRepository imageJdbcRepository;
    private final PostJdbcRepository postJdbcRepository;


    @Transactional
    @Override
    public void save(Long memberId, CartRequestDto cartRequestDto) {

        Optional.ofNullable( postJdbcRepository.findByPostId(cartRequestDto.getPostId()) )
                .orElseThrow(() -> new NotFoundException(ErrorCode.PostsNotFound));

        ImageDto imageDto = imageJdbcRepository.findTop1ByPostId(cartRequestDto.getPostId());

        CartDto cartDto = CartDto.builder()
                .memberId(memberId)
                .postId(cartRequestDto.getPostId())
                .quantity(cartRequestDto.getQuantity())
                .imageId(imageDto.getImageId())
                .build();
        cartJdbcRepository.save(cartDto);
    }

    @Override
    public byte[] getImageFileTop1(Long postId) {

        ImageDto imageDto = imageJdbcRepository.findTop1ByPostId(postId);

        if(!imageFileService.validImageFile(imageDto.getFilePath())){
            throw new NotFoundException(ErrorCode.ImageFileNotFound);
        }
        return imageFileService.downloadImageFile(imageDto.getFilePath());
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

