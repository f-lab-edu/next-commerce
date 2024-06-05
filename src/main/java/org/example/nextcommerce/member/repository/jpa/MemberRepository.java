package org.example.nextcommerce.member.repository.jpa;

import org.example.nextcommerce.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

    boolean existsByEmail(String email);


}
