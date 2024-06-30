package org.example.nextcommerce.image.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.nextcommerce.post.entity.Post;
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
    @Column(name = "IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String originalName;

    @Column(name = "PATH")
    private String filePath;

    @Column(name = "SIZE")
    private Long fileSize;

    private LocalDateTime createdAt;

    public Image(Post post,String originalName, String filePath, Long fileSize){
        this.post = post;
        this.originalName = originalName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }



}
