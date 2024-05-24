package org.example.nextcommerce.common.annotation;

import java.lang.annotation.*;

/**
 * 로그인된 사용자 정보 가져오기 리졸버
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginMember {
}
