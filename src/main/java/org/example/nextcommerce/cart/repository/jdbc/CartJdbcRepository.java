package org.example.nextcommerce.cart.repository.jdbc;

import org.example.nextcommerce.cart.dto.CartDto;

import javax.swing.tree.RowMapper;
import java.util.List;

public interface CartJdbcRepository {

    public void save(CartDto cartDto);
    public List<CartDto> findAllByMemberId(Long memberId);
}
