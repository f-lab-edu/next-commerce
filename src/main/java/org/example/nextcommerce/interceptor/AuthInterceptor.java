package org.example.nextcommerce.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.exception.UnauthorizedException;
import org.example.nextcommerce.service.SessionLoginService;
import org.example.nextcommerce.utils.annotation.LoginRequired;
import org.example.nextcommerce.utils.errormessage.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final SessionLoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class)){
            Long memberId = loginService.getSessionMemberId();
            if (memberId == null){
                throw new UnauthorizedException(ErrorCode.SessionIsNull);
            }
            logger.info(Long.toString(memberId));
        }

        return true;
    }
}
