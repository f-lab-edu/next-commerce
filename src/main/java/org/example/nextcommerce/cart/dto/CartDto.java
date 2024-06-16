package org.example.nextcommerce.cart.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartDto {

    private Long cartId;
    private Long memberId;
    private Long postId;
    private Integer quantity;

    public CartDto(Long memberId, Long postId, Integer quantity){
        this.memberId = memberId;
        this.postId = postId;
        this.quantity = quantity;
    }


}
