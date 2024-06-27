package org.example.nextcommerce.member.repository.jpa;

import org.example.nextcommerce.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberJpaRepository extends CrudRepository<Member, Long> {

    boolean existsByEmail(String email);
    public Optional<Member> findMemberByEmail(String email);


}
