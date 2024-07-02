package org.example.nextcommerce.image.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.nextcommerce.post.entity.Post;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor()
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalName;

    @Column(name = "path")
    private String filePath;

    @Column(name = "size")
    private Long fileSize;

    @CreatedDate
    private LocalDateTime createdAt;

    public Image(Post post,String originalName, String filePath, Long fileSize){
        this.post = post;
        this.originalName = originalName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }



}
