package org.example.nextcommerce.dao;

import org.example.nextcommerce.dto.MemberDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberMapper implements RowMapper<MemberDto> {

    @Override
    public MemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        MemberDto dto = new MemberDto(
                rs.getLong("member_id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("zip_code"),
                rs.getString("address"),
                rs.getString("detail_address"),
                rs.getString("extra_address")
        );
        return dto;
    }
}
