package org.example.nextcommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.common.exception.BadRequestException;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.exception.MemberNotFoundException;
import org.example.nextcommerce.repository.jdbc.MemberJdbcRepository;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.common.utils.validation.ValidCheck;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberJdbcRepository memberJdbcRepository;

    private ValidCheck validCheck;

    public void create(MemberDto dto){
        dto.passwordCrypt(passwordEncoder);
        if(memberJdbcRepository.save(dto) == 0){
            throw new DatabaseException(ErrorCode.DBInsertFail);
        }
    }

    public boolean isDuplicatedEmail(String email){
        return (memberJdbcRepository.findByEmail(email) == null) ? false : true;
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
        if(!ValidCheck.isEmailValidation(memberDto.getEmail())){
            throw new BadRequestException(ErrorCode.MemberEmailValidationFailed);
        }
        if(!ValidCheck.isPasswordValidation(memberDto.getPassword())){
            throw new BadRequestException(ErrorCode.MemberPwValidationFailed);
        }
        return true;
   }

}
