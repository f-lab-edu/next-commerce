package org.example.nextcommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dao.MemberDao;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.utils.ReturnType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberDao memberDao;

    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){

            String validKeyName = String.format("valid%c%s", Character.toUpperCase(error.getField().charAt(0)) ,error.getField().substring(1));
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
    public ReturnType saveMember(MemberDto dto){
        if(memberDao.FindByEmail(dto.getEmail()) != null){
            return ReturnType.EXIST_EMAIL;
        }
        dto.passwordCrypt(passwordEncoder);

        if(memberDao.save(dto) == 0){
            return ReturnType.FAIL;
        }
        return ReturnType.SUCCESS;
    }


}
