package org.example.nextcommerce.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.common.annotation.LoginMember;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.common.exception.UnauthorizedException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.repository.jdbc.MemberJdbcRepository;
import org.example.nextcommerce.repository.jpa.MemberRepository;
import org.example.nextcommerce.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Parameter;

@Component
@RequiredArgsConstructor
public class LoginMemberInterceptor implements HandlerInterceptor {

    private final MemberJdbcRepository memberJdbcRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Parameter[] parameters = handlerMethod.getMethod().getParameters();
            for(Parameter parameter : parameters){
                if(parameter.isAnnotationPresent(LoginMember.class)){
                    HttpSession httpSession = request.getSession(false);
                    Long memberId = (Long) httpSession.getAttribute("MemberId");
                    if (memberId == null){
                        throw new UnauthorizedException(ErrorCode.SessionIsNull);
                    }
                    MemberDto memberDto = memberJdbcRepository.findByMemberId(memberId);
                    request.setAttribute("MemberDto", memberDto);
                }
            }
        }

        return true;

    }
}
