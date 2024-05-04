package org.example.nextcommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.MemberForm;
import org.example.nextcommerce.entity.Member;
import org.example.nextcommerce.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    /*
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    */

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public boolean checkEmailDuplicate(String email){
        return memberRepository.existsByEmail(email);
    }

    public Member create(MemberForm dto){
        Member member = dto.toEntity(passwordEncoder);
        if(member.getId() != null){
            return null;
        }
        return memberRepository.save(member);
    }


}
