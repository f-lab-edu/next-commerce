package org.example.nextcommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.MemberForm;
import org.example.nextcommerce.entity.Member;
import org.example.nextcommerce.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public Member create(MemberForm dto){
        Member member = dto.toEntity();
        if(member.getId() != null){
            return null;
        }



        return memberRepository.save(member);
    }


}
