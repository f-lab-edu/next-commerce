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
    private Long imageId;
    private Integer quantity;


    public CartDto(Long memberId, Long postId,  Long imageId, Integer quantity){
        this.memberId = memberId;
        this.postId = postId;
        this.imageId = imageId;
        this.quantity = quantity;
    }


}
