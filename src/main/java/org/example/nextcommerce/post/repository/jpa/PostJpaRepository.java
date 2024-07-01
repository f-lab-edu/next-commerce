package org.example.nextcommerce.post.repository.jpa;

import org.example.nextcommerce.post.dto.PostDto;
import org.example.nextcommerce.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {


}
