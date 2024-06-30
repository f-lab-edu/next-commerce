package org.example.nextcommerce.cart.repository.jpa;

import org.example.nextcommerce.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    @Query(value = "select c from Cart c where c.member.id = :memberId")
    List<Cart> findAllByMemberId(Long memberId);

    @Query(value = "delete from Cart c where c.member.id = :memberId")
    void deleteAllByMemberId(Long memberId);

}
