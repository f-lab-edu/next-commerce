package org.example.nextcommerce.post.repository.jpa;

import org.example.nextcommerce.post.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
