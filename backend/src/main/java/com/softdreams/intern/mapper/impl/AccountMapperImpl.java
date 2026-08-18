package com.softdreams.intern.mapper.impl;

import com.softdreams.intern.dto.response.AccountResponse;
import com.softdreams.intern.entity.Account;
import com.softdreams.intern.mapper.AccountMapper;
import com.softdreams.intern.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final RoleMapper roleMapper;

    @Override
    public AccountResponse toResponse(Account account) {
        if (account == null)
            return null;

        AccountResponse response = new AccountResponse();

        response.setUsername(account.getUsername());
        response.setPassword(account.getPassword());
        response.setRole(roleMapper.toResponse(account.getRole()));

        return response;
    }
}
