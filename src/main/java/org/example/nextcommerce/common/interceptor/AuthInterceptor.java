package org.example.nextcommerce.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.common.exception.UnauthorizedException;
import org.example.nextcommerce.common.annotation.LoginRequired;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.logging.Handler;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class)){

            HttpSession httpSession = request.getSession();
            Long memberId = (Long) httpSession.getAttribute("MemberId");
            if (memberId == null){
                throw new UnauthorizedException(ErrorCode.SessionIsNull);
            }
            logger.info(Long.toString(memberId));
        }

        return true;
    }
}
