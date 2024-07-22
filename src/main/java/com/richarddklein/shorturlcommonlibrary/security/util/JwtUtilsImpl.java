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
import reactor.core.publisher.Mono;

public class JwtUtilsImpl implements JwtUtils {
    private final ParameterStoreReader parameterStoreReader;

    public JwtUtilsImpl(ParameterStoreReader parameterStoreReader) {
        this.parameterStoreReader = parameterStoreReader;
    }

    @Override
    public Mono<String> generateToken(UsernameAndRole usernameAndRole) {
        Date now = new Date();
        return parameterStoreReader
                .getJwtMinutesToLive().flatMap(minutesToLive -> {

            long timeToLive = TimeUnit.MINUTES.toMillis(minutesToLive);
            Date expirationDate = new Date(now.getTime() + timeToLive);

            return getKey().map(key -> Jwts.builder()
                    .subject(usernameAndRole.getUsername())
                    .claim("role", usernameAndRole.getRole())
                    .issuedAt(now)
                    .expiration(expirationDate)
                    .signWith(key)
                    .compact());
        });
    }

    @Override
    public Mono<UsernameAndRole> extractUsernameAndRoleFromToken(String token) {
        return getKey().map(key -> {
            Claims payload = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = payload.getSubject();
            String role = payload.get("role", String.class);

            return new UsernameAndRole(username, role);
        });
    }

    private Mono<SecretKey> getKey() {
        return parameterStoreReader.getJwtSecretKey().map(key -> {
            byte[] keyBytes = Decoders.BASE64.decode(key);
            return Keys.hmacShaKeyFor(keyBytes);
        });
    }
}
