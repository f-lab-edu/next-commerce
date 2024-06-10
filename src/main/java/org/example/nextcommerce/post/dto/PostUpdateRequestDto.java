package org.example.nextcommerce.post.dto;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostUpdateRequestDto {
    private String postTitle;
    private String postContent;
    private String productPrice;
}
