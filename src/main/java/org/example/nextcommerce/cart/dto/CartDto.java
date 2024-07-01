package org.example.nextcommerce.cart.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartDto {

    private Long cartId;
    private Long memberId;
    private Long productId;
    private Long imageId;
    private Integer quantity;


    public CartDto(Long memberId, Long productId,  Long imageId, Integer quantity){
        this.memberId = memberId;
        this.productId = productId;
        this.imageId = imageId;
        this.quantity = quantity;
    }


}
