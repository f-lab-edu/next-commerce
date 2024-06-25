package org.example.nextcommerce.cart.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartResponseDto {
    private Long cartId;
    private Long memberId;
    private Long postId;
    private Integer quantity;
    private byte[] imageFile;
}
