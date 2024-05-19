package org.example.nextcommerce.dto;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Getter
@Component
@Scope( value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long memberId;
    private String email;

    public void updateLoginDto(Long memberId, String email){
        this.memberId = memberId;
        this.email = email;
    }

}
