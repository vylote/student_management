package com.softdreams.intern.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.softdreams.intern.config.JwtProperties;
import com.softdreams.intern.entity.Account;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    final JwtProperties jwtProperties;

    @Override
    public String generateToken(Account account) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

            Instant now = Instant.now();

            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(account.getUsername())
                    .issuer("school_management")
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plus(24, ChronoUnit.HOURS)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("role", account.getRole().getCode())
                    .claim("id", account.getId())
                    .build();

            Payload payload = new Payload(jwtClaimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(jwtProperties.getSignerKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SignedJWT verifyToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(jwtProperties.getSignerKey().getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            boolean verified = signedJWT.verify(verifier);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            if (!verified || expiryTime.before(new Date())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            return signedJWT;
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
