/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;

import com.richarddklein.shorturlcommonlibrary.aws.ParameterStoreReader;
import com.richarddklein.shorturlcommonlibrary.security.dto.UsernameAndRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtilsImpl implements JwtUtils {
    private final ParameterStoreReader parameterStoreReader;

    public JwtUtilsImpl(ParameterStoreReader parameterStoreReader) {
        this.parameterStoreReader = parameterStoreReader;
    }

    @Override
    public String generateToken(UsernameAndRole usernameAndRole) {
        Date now = new Date();
        long timeToLive = TimeUnit.MINUTES.toMillis(
                parameterStoreReader.getJwtMinutesToLive());
        Date expirationDate = new Date(now.getTime() + timeToLive);

        return Jwts.builder()
                .subject(usernameAndRole.getUsername())
                .claim("role", usernameAndRole.getRole())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getKey())
                .compact();
    }

    @Override
    public UsernameAndRole extractUsernameAndRoleFromToken(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = payload.getSubject();
        String role = payload.get("role", String.class);

        return new UsernameAndRole(username, role);
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(
                parameterStoreReader.getJwtSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
