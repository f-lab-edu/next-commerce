package org.example.nextcommerce.cart.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartRequestDto {
    private Long postId;
    private Long productId;
    private Integer quantity;
}
