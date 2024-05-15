package org.example.nextcommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberDto {
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]{2,12})@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){2,12}[.][a-zA-Z]{2,3}$", message="이메일 주소 양식을 확인해주세요")
    private String email;

    @NotEmpty
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문 대,소문자와 숫자,특수기호를 포함한 8자~20자의 비밀번호여야 합니다.")
    private String password;

    public void passwordCrypt(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }


}
