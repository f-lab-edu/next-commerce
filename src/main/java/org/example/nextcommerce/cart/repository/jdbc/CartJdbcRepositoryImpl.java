package org.example.nextcommerce.cart.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartJdbcRepositoryImpl implements CartJdbcRepository{

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<CartDto> cartDtoRowMapper(){
        return ((rs, rowNum) -> {
           CartDto dto = CartDto.builder()
                   .cartId(rs.getLong("cart_id"))
                   .memberId(rs.getLong("member_id"))
                   .postId(rs.getLong("post_id"))
                   .imageId(rs.getLong("image_id"))
                   .quantity(rs.getInt("quantity"))
                   .build();
           return dto;
        });
    }

    @Override
    public void save(CartDto cartDto) {
        String sql = "INSERT INTO cart (member_id, post_id,image_id ,quantity) "
                + "VALUES (?,?,?,?)";
        if (jdbcTemplate.update(sql, cartDto.getMemberId(), cartDto.getPostId(),cartDto.getImageId() ,cartDto.getQuantity()) == 0){
            throw new DatabaseException(ErrorCode.CartInsertFail);
        }
    }

    @Override
    public List<CartDto> findAllByMemberId(Long memberId) {
        String sql = "SELECT * FROM cart WHERE member_id=?";
        return jdbcTemplate.query(sql, cartDtoRowMapper(), memberId);
    }

    @Override
    public void deleteByCartId(Long cartId) {
        String sql = "DELETE FROM cart WHERE cart_id=?";
        if(jdbcTemplate.update(sql, cartId) != 1){
            throw new DatabaseException(ErrorCode.CartDeleteFail);
        }
    }

    @Override
    public void deleteAllByMemberId(Long memberId) {
        String sql = "DELETE FROM cart WHERE member_id=?";
        if(jdbcTemplate.update(sql,memberId) < 1){
            throw new DatabaseException(ErrorCode.CartDeleteFail);
        }

    }
}
