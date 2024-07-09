package org.example.nextcommerce.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.member.domain.dto.MemberDto;
import org.example.nextcommerce.common.exception.BadRequestException;
import org.example.nextcommerce.common.exception.MemberNotFoundException;
import org.example.nextcommerce.member.repository.jdbc.MemberJdbcRepository;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberJdbcService implements MemberService<MemberDto>{

    private final PasswordEncoder passwordEncoder;
    private final MemberJdbcRepository memberJdbcRepository;

    @Override
    public void create(MemberDto dto){
        dto.passwordCrypt(passwordEncoder);
        memberJdbcRepository.save(dto);
    }

    @Override
    public void checkDuplicatedEmail(String email){
        if(memberJdbcRepository.findByEmail(email) != null){
            throw new BadRequestException(ErrorCode.MemberDuplicatedEmail);
        }
    }

    @Override
    public Long checkValidMember(MemberDto inputMemberDto){
        MemberDto dbDto = memberJdbcRepository.findByEmail(inputMemberDto.getEmail());
        if(dbDto == null){
            throw new MemberNotFoundException(ErrorCode.MemberNotFound);
        }
        if(!passwordEncoder.matches(inputMemberDto.getPassword(), dbDto.getPassword())){
            throw new BadRequestException(ErrorCode.MemberPasswordMismatch);
        }
        return dbDto.getId();
    }

    @Override
   public boolean isValidMemberDto(MemberDto memberDto){
        if(!memberDto.isValidEmail()){
            throw new BadRequestException(ErrorCode.MemberEmailValidationFailed);
        }
        if(!memberDto.isValidPassword()){
            throw new BadRequestException(ErrorCode.MemberPwValidationFailed);
        }
        return true;
   }

   @Override
   public void deleteMember(Long memberId){
        memberJdbcRepository.deleteByMemberId(memberId);
   }

    @Override
    public MemberDto checkLoginMember(Long memberId) {
        return memberJdbcRepository.findByMemberId(memberId);
    }
}
