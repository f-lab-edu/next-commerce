package org.example.nextcommerce.post.dto;

import lombok.*;
import org.example.nextcommerce.post.entity.Product;

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

    public Product toEntity(){
        return Product.builder()
                .id(this.productId)
                .code(this.code)
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .build();
    }

}
