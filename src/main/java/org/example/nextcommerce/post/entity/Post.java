package org.example.nextcommerce.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.nextcommerce.member.entity.Member;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Lob
    private String content;

    private String title;

    private String category;

    public Post(Member member, Product product, String content, String title, String category){
        this.member = member;
        this.product = product;
        this.content = content;
        this.title = title;
        this.category = category;
    }

    public void update(String content, String title){
        this.content = content;
        this.title = title;
    }



}
