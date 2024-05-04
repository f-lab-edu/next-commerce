package org.example.nextcommerce.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.MemberForm;
import org.example.nextcommerce.entity.Member;
import org.example.nextcommerce.repository.MemberRepository;
import org.example.nextcommerce.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Array;
import java.util.Map;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/signup")
    public String newMemberForm(){
        return "members/new";
    }

    @PostMapping("/join")
    public String createMember(@Valid MemberForm form, Errors errors, Model model){
        log.info(form.toString());
        if(errors.hasErrors()){
            model.addAttribute("Member", form);
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
                log.info(key);
                log.info(validatorResult.get(key));
            }

            if(!validatorResult.containsKey("valid_email")) {
                model.addAttribute("valid_email", null);
            }
            if(!validatorResult.containsKey("valid_password")){
                model.addAttribute("valid_password", null);
            }

            return "members/renew";
        }

        Member member = form.toEntity();
        log.info(member.toString());

        Member created = memberService.create(form);
        if(created == null){
            return "pages/404";
        }

        return "/pages/index";
    }

    @GetMapping("pages")
    public String test(){
        return "pages/404";
    }

}
