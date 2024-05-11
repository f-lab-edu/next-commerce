package org.example.nextcommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.exception.DatabaseException;
import org.example.nextcommerce.repository.jdbc.MemberJdbcRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberJdbcRepository memberJdbcRepository;


    public boolean create(MemberDto dto){
        dto.passwordCrypt(passwordEncoder);
        if(memberJdbcRepository.save(dto) == 0){
            throw new DatabaseException();
        }
        return true;
    }

    public boolean isDuplicatedEmail(String email){
        return (memberJdbcRepository.findByEmail(email) == null) ? false : true;
    }


}
