package org.example.nextcommerce.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.member.dto.MemberDto;
import org.example.nextcommerce.common.exception.BadRequestException;
import org.example.nextcommerce.common.exception.MemberNotFoundException;
import org.example.nextcommerce.member.repository.jdbc.MemberJdbcRepository;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberJdbcRepository memberJdbcRepository;

    public void create(MemberDto dto){
        dto.passwordCrypt(passwordEncoder);
        memberJdbcRepository.save(dto);
    }

    public void checkDuplicatedEmail(String email){
        if(memberJdbcRepository.findByEmail(email) != null){
            throw new BadRequestException(ErrorCode.MemberDuplicatedEmail);
        }
    }

    public MemberDto checkValidMember(MemberDto inputMembetDto){
        MemberDto dbDto = memberJdbcRepository.findByEmail(inputMembetDto.getEmail());
        if(dbDto == null){
            throw new MemberNotFoundException(ErrorCode.MemberNotFound);
        }
        if(!passwordEncoder.matches(inputMembetDto.getPassword(), dbDto.getPassword())){
            throw new BadRequestException(ErrorCode.MemberPasswordMismatch);
        }
        return dbDto;
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

   public void deleteMember(Long memberId){
        memberJdbcRepository.deleteByMemberId(memberId);
   }



}
