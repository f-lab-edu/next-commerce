package org.example.nextcommerce.resolver;

import org.example.nextcommerce.common.annotation.LoginMember;
import org.example.nextcommerce.common.resolver.LoginMemberArgumentResolver;
import org.example.nextcommerce.member.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginMemberArgumentResolverTest {
    void testMethod(@LoginMember String notMemberDto){

    }

    @DisplayName("@LoginMember의 파라미터가 MemberDto 클래스가 아니면 지원하지 않는다. ")
    @Test
    public void failSupportsParameterNotMemeberDto() throws NoSuchMethodException {
        //given
        LoginMemberArgumentResolver loginMemberArgumentResolver = new LoginMemberArgumentResolver();

        //when
        Method tMethod = LoginMemberArgumentResolverTest.class.getDeclaredMethod("testMethod", String.class);

        //then
        MethodParameter methodParameter = new MethodParameter(tMethod, 0);
        assertFalse(loginMemberArgumentResolver.supportsParameter(methodParameter));

    }

}
