package org.example.nextcommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.example.nextcommerce.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberDto {
    private Long id;

    private String email;

    private String password;

    public void passwordCrypt(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }
    public void updateMemberId(Long memberId){
        this.id = memberId;
    }

}
