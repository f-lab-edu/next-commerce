package org.example.nextcommerce.cart.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.nextcommerce.image.entity.Image;
import org.example.nextcommerce.member.entity.Member;
import org.example.nextcommerce.post.entity.Post;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @OneToOne
    @JoinColumn(name = "IMAGE_ID")
    private Image image;

    private Integer quantity;

    public void update(Integer quantity){
        this.quantity = quantity;
    }

}
