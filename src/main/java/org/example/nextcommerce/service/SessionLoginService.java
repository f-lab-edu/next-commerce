package org.example.nextcommerce.service;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.LoginDto;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.common.exception.UnauthorizedException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionLoginService {

    private final HttpSession httpSession;

    @Resource
    private LoginDto loginDto;

    public void saveLoginMember(MemberDto memberDto){

        loginDto.updateLoginDto(memberDto.getId(), memberDto.getEmail());
        httpSession.setAttribute("MemberId", loginDto.getMemberId());
        log.info(loginDto.toString());
    }

    public Long getSessionMemberId(){
        Long id = (Long) httpSession.getAttribute("MemberId");
        if(id == null){
            throw new UnauthorizedException(ErrorCode.SessionIsNull);
        }
        return id;

    }


}
