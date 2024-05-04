package org.example.nextcommerce.validator;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.dto.MemberForm;
import org.example.nextcommerce.repository.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckMemberEmailValidator extends AbstractValidator<MemberForm>{

    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberForm form, Errors errors){
        if(memberRepository.existsByEmail(form.toEntity().getEmail())){
            errors.rejectValue("email","Duplicate validate error","이미 사용중인 이메일 입니다.");
        }
    }

}
