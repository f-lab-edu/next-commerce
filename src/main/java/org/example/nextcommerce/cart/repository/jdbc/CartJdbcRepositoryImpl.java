package org.example.nextcommerce.cart.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.cart.dto.CartDto;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
                   .quantity(rs.getInt("quantity"))
                   .build();
           return dto;
        });
    }

    @Override
    public void save(CartDto cartDto) {
        String sql = "INSERT INTO cart (member_id, post_id, quantity) "
                + "VALUES (?,?,?)";
        if (jdbcTemplate.update(sql, cartDto.getMemberId(), cartDto.getPostId(), cartDto.getQuantity()) == 0){
            throw new DatabaseException(ErrorCode.CartInsertFail);
        }
    }
}
