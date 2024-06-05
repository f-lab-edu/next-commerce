package org.example.nextcommerce.member.dto;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

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

    public boolean isValidMemberId(Long memberId){
        return this.id.equals(memberId);
    }

    public boolean isEmailValidation(){
        return Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]{2,12})@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){2,12}[.][a-zA-Z]{2,3}$", this.email);
    }

    public boolean isPasswordValidation(){
        return Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", this.password);
    }


}
