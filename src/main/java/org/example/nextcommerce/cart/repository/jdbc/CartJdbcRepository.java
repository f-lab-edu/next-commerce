package org.example.nextcommerce.cart.repository.jdbc;

import org.example.nextcommerce.cart.dto.CartDto;

import javax.swing.tree.RowMapper;

public interface CartJdbcRepository {

    public void save(CartDto cartDto);
}
