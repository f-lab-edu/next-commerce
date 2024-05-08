package org.example.nextcommerce.dao;

import org.example.nextcommerce.dto.MemberDto;
import org.example.nextcommerce.utils.ReturnType;

public interface MemberDao {
    public int save(MemberDto dto);
    public MemberDto FindByEmail(String email);
}
