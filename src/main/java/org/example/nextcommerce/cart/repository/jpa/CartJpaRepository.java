package org.example.nextcommerce.cart.repository.jpa;

import org.example.nextcommerce.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMemberId(Long memberId);

    @Modifying
    @Query(value = "delete from Cart c where c.member.id = :memberId")
    void deleteAllByMemberId(Long memberId);

}
