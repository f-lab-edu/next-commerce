package org.example.nextcommerce.post.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostDto {

    private Long postId;
    private Long memberId;
    private Long productId;
    private String content;
    private String category;
    private String title;

    public void updatePostId(Long postId){
        this.postId = postId;
    }
    public void updatePostDto(String content, String title){
        this.content = content;
        this.title = title;
    }

}
