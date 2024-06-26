package org.example.nextcommerce.post.repository.jdbc;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.post.dto.ProductDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class ProductJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<ProductDto> productDtoRowMapper(){
        return ((rs, rowNum) ->{
           ProductDto dto = ProductDto.builder()
                   .productId(rs.getLong("product_id"))
                   .code(rs.getString("code"))
                   .name(rs.getString("name"))
                   .price(rs.getString("price"))
                   .stock(rs.getInt("stock"))
                   .build();
           return dto;
        });
    }

    public Long save(ProductDto dto){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO products (code, name, price, stock) "
                + "VALUES (?,?,?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

                PreparedStatement pstmt = con.prepareStatement(
                        sql, new String[] {"product_id"});
                pstmt.setString(1, dto.getCode());
                pstmt.setString(2, dto.getName());
                pstmt.setString(3, dto.getPrice());
                pstmt.setInt(4, dto.getStock());
                return pstmt;
            }
        }, keyHolder);

        if(keyHolder.getKey() == null){
            throw new DatabaseException(ErrorCode.DBDataIdNotFound);
        }

        dto.updateId(keyHolder.getKey().longValue());
        return dto.getProductId();
    }

    public void deleteByProductId(Long productId){
        String sql = "DELETE FROM products WHERE product_id=?";
        if(jdbcTemplate.update(sql, productId) != 1){
            throw new DatabaseException(ErrorCode.ProductsDeleteFail);
        }
    }

    public ProductDto findByProductId(Long productId){
        String sql = "SELECT * FROM products WHERE product_id=?";
        ProductDto dto;
        try{
            dto = jdbcTemplate.queryForObject(sql, productDtoRowMapper(), productId);
        }catch (EmptyResultDataAccessException e){
            throw new DatabaseException(ErrorCode.ProductsNotFound);
        }
        return dto;
    }

    public void update(Long productId, String price){
        String sql = "UPDATE products SET price=? WHERE product_id=?";
        if(jdbcTemplate.update(sql, price, productId ) ==0){
            throw new DatabaseException(ErrorCode.ProductUpdateFail);
        }

    }



}
