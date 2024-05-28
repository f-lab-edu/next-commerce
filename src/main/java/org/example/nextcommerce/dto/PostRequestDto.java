package org.example.nextcommerce.dto;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostRequestDto {

    private String productName;
    private String productPrice;
    private Integer productStock;
    private String postTitle;
    private String postContent;
    private String postCategory;

}
