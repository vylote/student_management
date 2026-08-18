package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.response.AccountResponse;
import com.softdreams.intern.entity.Account;

public interface AccountMapper {

    AccountResponse toResponse(Account account);
}
