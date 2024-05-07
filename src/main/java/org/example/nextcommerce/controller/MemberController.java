package org.example.nextcommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.service.MemberService;
import org.example.nextcommerce.utils.ReturnType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String newMemberForm(){
        return "members/new";
    }

    @PostMapping("/join")
    public String createMember(@Valid MemberDto dto, Errors errors, Model model){
        //log.info(dto.toString());
        if(errors.hasErrors()){
            model.addAttribute("Member", dto);
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }

            if(!validatorResult.containsKey("validEmail")) {
                model.addAttribute("validEmail", null);
            }
            if(!validatorResult.containsKey("validPassword")){
                model.addAttribute("validPassword", null);
            }

            return "members/renew";
        }

        if(memberService.saveMember(dto) == ReturnType.EXIST_EMAIL) {
            model.addAttribute("Member", dto);
            model.addAttribute("validEmail", "이미 사용중인 이메일입니다");
            return "members/renew";
        }

        return "pages/index";
    }

}
