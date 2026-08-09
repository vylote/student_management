package com.softdreams.intern.service;

import com.softdreams.intern.entity.Account;

public interface JwtService {
    public String generateToken(Account account);
}
