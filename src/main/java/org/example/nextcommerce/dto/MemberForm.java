package org.example.nextcommerce.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;
import org.example.nextcommerce.entity.Address;
import org.example.nextcommerce.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@ToString
public class MemberForm {
    private Long id;

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$", message="이메일 주소 양식을 확인해주세요")
    private String email;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문 대,소문자와 숫자,특수기호를 포함한 8자~20자의 비밀번호여야 합니다.")
    private String password;
    private Address address;

    public MemberForm(Long id, String email, String password, String zipCode, String address, String detailAddress, String extraAddress){
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = new Address(zipCode, address, detailAddress, extraAddress);
    }

    public Member toEntity(PasswordEncoder passwordEncoder){

        return new Member(email, passwordEncoder.encode(password), address);
    }



}
