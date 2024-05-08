package org.example.nextcommerce.dao;

import org.example.nextcommerce.dto.MemberDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class MemberDaoImpl implements MemberDao{

    private final JdbcTemplate jdbcTemplate;
    public MemberDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(MemberDto dto) {
        String sql = "INSERT INTO member (address ,detail_address, extra_address, zip_code,created_time ,email,modified_time ,password)"
                + " VALUES (?, ?, ?, ?, now(), ?, now() , ?)";

        return jdbcTemplate.update(sql,dto.getAddress().getAddress(), dto.getAddress().getDetailAddress(), dto.getAddress().getExtraAddress(), dto.getAddress().getZipCode(), dto.getEmail(), dto.getPassword());
    }

    @Override
    public MemberDto FindByEmail(String email) {
        String sql = " SELECT * FROM member WHERE email=?";
        MemberDto dto;
        try{
            dto = jdbcTemplate.queryForObject(sql, new MemberMapper(), email);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

        return dto;
    }
}
