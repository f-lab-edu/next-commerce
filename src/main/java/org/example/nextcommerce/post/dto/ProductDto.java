package org.example.nextcommerce.post.dto;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductDto {
    private Long productId;
    private String code;
    private String name;
    private String price;
    private Integer stock;
    public void updateId(Long productId){
        this.productId = productId;
    }
}
