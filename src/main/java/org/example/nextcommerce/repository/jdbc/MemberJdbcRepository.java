package org.example.nextcommerce.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.dto.MemberDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class MemberJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<MemberDto> memberDtoRowMapper() {
        return ((rs, rowNum) -> {
            MemberDto dto = new MemberDto(
                    rs.getLong("member_id"),
                    rs.getString("email"),
                    rs.getString("password")
            );
            return dto;
        });
    }

    public MemberDto findByEmail(String email) {
        String sql = " SELECT * FROM members WHERE email=?";
        MemberDto dto;
        try{
            dto = jdbcTemplate.queryForObject(sql, memberDtoRowMapper() , email);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

        return dto;
    }

    public int save(MemberDto dto){
        String sql = "INSERT INTO members (email, password, created_time, modified_time) "
                + "VALUES (?,?,now(), now())";
        return jdbcTemplate.update(sql, dto.getEmail(), dto.getPassword());
    }

    public MemberDto findByMemberId(Long memberId) {
        String sql = " SELECT * FROM members WHERE member_id=?";
        MemberDto dto;
        try{
            dto = jdbcTemplate.queryForObject(sql, memberDtoRowMapper() , memberId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

        return dto;
    }

    public int deleteByMemberId(Long memberId){
        String sql = "DELETE FROM members WHERE member_id=?";
        return jdbcTemplate.update(sql, Long.valueOf(memberId));

    }

}
