package org.example.nextcommerce.repository;

import org.example.nextcommerce.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
