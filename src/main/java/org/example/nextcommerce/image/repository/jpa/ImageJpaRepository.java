package org.example.nextcommerce.image.repository.jpa;

import org.example.nextcommerce.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    @Query(value = "select i from Image i where i.post.id = :postId ORDER BY i.createdAt desc limit 1")
    Optional<Image> findByCreatedAtByPostId(Long postId);

    @Query(value = "delete from Image i where i.post.id = :postId")
    void deleteAllByPostId(Long postId);

}
