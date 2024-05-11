package org.example.nextcommerce.service;

import org.example.nextcommerce.repository.jdbc.MemberJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberJdbcRepository memberJdbcRepository;


}
