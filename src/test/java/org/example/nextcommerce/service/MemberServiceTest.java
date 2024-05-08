package org.example.nextcommerce.service;

import com.zaxxer.hikari.HikariDataSource;
import org.example.nextcommerce.dao.MemberDao;
import org.example.nextcommerce.dao.MemberDaoImpl;
import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.jdbc.DBInfo;
import org.example.nextcommerce.utils.CommonConfig;
import org.example.nextcommerce.utils.ReturnType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberServiceTest {
    public MemberService memberService;
    public MemberDao memberDao;
    ApplicationContext context = new AnnotationConfigApplicationContext(CommonConfig.class);
    public HikariDataSource dataSource;

    @BeforeEach
    public void setUp(){

        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(DBInfo.DB_URL.info());
        dataSource.setUsername(DBInfo.DB_USER_NAME.info());
        dataSource.setPassword(DBInfo.DB_PASSWORD.info());
        memberDao = new MemberDaoImpl(dataSource);
        memberService = new MemberService(context.getBean(PasswordEncoder.class), memberDao);

    }


    @Test
    @DisplayName("성공 존재하지 않는 email 입력시 ReturnType.SUCCESS를 반환")
    @Transactional
    public void create_success_no_exist_email(){
        MemberDto expected = new MemberDto(null, "springg@naver.com","asdf*789", "12345" , "제주", "한라산", "101호");
        System.out.println(expected.toString());
        assertEquals(memberService.saveMember(expected), ReturnType.SUCCESS);
    }






}
