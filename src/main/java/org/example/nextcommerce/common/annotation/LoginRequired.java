package org.example.nextcommerce.common.annotation;

import java.lang.annotation.*;

/**
 * 로그인 확인 인터셉터
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
