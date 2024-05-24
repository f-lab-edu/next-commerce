package org.example.nextcommerce.common.utils;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class SessionUtils {

    private SessionUtils(){}

    public static HttpSession getSession(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest().getSession();
    }

    public static void setLoginSessionMemberId(Long memberId){
        SessionUtils.getSession().setAttribute("MemberId", memberId);
    }

    public static Long getLoginSessionMemberId(){
        return (Long) SessionUtils.getSession().getAttribute("MemberId");
    }

    public static String getSessionId(){
        return SessionUtils.getSession().getId();
    }


}
