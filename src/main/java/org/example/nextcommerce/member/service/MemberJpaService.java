package org.example.nextcommerce.member.service;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.common.exception.BadRequestException;
import org.example.nextcommerce.common.exception.MemberNotFoundException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.member.domain.dto.MemberDto;
import org.example.nextcommerce.member.domain.entity.Member;
import org.example.nextcommerce.member.repository.jpa.MemberJpaRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberJpaService{

    private final MemberJpaRepository memberJpaRepository;
   // private MemberMapper memberMapper;

    public void create(MemberDto memberDto) {
        memberJpaRepository.save(memberDto.toEntity());
    }

    public void checkDuplicatedEmail(String email) {
        if(memberJpaRepository.findMemberByEmail(email).isPresent()){  //null이 아닌 경우
            throw new BadRequestException(ErrorCode.MemberDuplicatedEmail);
        }
    }

    public Long checkValidMember(Member inputMember) {
        Member member = memberJpaRepository.findMemberByEmail(inputMember.getEmail()).orElseThrow(() -> new BadRequestException(ErrorCode.MemberNotFound));
       return member.getId();
    }


    public boolean isValidMemberDto(MemberDto memberDto){
        if(!memberDto.isValidEmail()){
            throw new BadRequestException(ErrorCode.MemberEmailValidationFailed);
        }
        if(!memberDto.isValidPassword()){
            throw new BadRequestException(ErrorCode.MemberPwValidationFailed);
        }
        return true;
    }


    public void deleteMember(Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }


    public MemberDto checkLoginMember(Long memberId){
        Member member = memberJpaRepository.findById(memberId).orElseThrow(()-> new MemberNotFoundException(ErrorCode.MemberNotFound));
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }
}
