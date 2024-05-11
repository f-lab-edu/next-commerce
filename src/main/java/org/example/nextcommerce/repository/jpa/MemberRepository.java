package org.example.nextcommerce.repository.jpa;

import org.example.nextcommerce.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

    boolean existsByEmail(String email);


}
