package org.example.nextcommerce.member.service;

import org.example.nextcommerce.member.domain.dto.MemberDto;

public interface MemberService<T> {
    public void create(T t);
    public void checkDuplicatedEmail(String email);
    public Long checkValidMember(T t);
    public boolean isValidMemberDto(MemberDto memberDto);
    public void deleteMember(Long memberId);
    public MemberDto checkLoginMember(Long memberId);
}
