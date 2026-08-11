package com.softdreams.intern.service;

import com.nimbusds.jwt.SignedJWT;
import com.softdreams.intern.entity.Account;

public interface JwtService {
    String generateToken(Account account);

    SignedJWT verifyToken(String token);
}
