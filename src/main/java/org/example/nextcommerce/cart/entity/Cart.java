package org.example.nextcommerce.cart.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.nextcommerce.image.entity.Image;
import org.example.nextcommerce.member.entity.Member;
import org.example.nextcommerce.post.entity.Post;
import org.example.nextcommerce.post.entity.Product;
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
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    private Integer quantity;

    public void update(Integer quantity){
        this.quantity = quantity;
    }

}
