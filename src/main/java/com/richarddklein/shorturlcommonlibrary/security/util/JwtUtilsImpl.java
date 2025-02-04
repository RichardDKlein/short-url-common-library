/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;

import com.richarddklein.shorturlcommonlibrary.environment.ParameterStoreAccessor;
import com.richarddklein.shorturlcommonlibrary.security.dto.UsernameAndRole;
import com.richarddklein.shorturlcommonlibrary.security.exception.ExpiredJwtException;
import com.richarddklein.shorturlcommonlibrary.security.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

public class JwtUtilsImpl implements JwtUtils {
    private final ParameterStoreAccessor parameterStoreAccessor;

    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------

    public JwtUtilsImpl(ParameterStoreAccessor parameterStoreAccessor) {
        this.parameterStoreAccessor = parameterStoreAccessor;
    }

    @Override
    public Mono<String> generateToken(UsernameAndRole usernameAndRole) {
        Date now = new Date();
        return parameterStoreAccessor
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
    public Mono<Authentication> authenticateToken(String token) {
        return extractUsernameAndRoleFromToken(token)
            .map(usernameAndRole -> {
                String username = usernameAndRole.getUsername();
                String role = usernameAndRole.getRole();

                List<SimpleGrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority(role));
                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                        username, null, authorities);

                return (Authentication)authenticationToken;
            }).onErrorResume(e -> {
                String errorMsg = getErrorMsg(e);
                System.out.println("====> JwtUtilsImpl.authenticateToken(): " + errorMsg);
                return (errorMsg.contains("JWT expired")) ?
                    Mono.error(new ExpiredJwtException(errorMsg)) :
                    Mono.error(new InvalidJwtException(errorMsg));
            });
    }

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

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private Mono<SecretKey> getKey() {
        return parameterStoreAccessor.getJwtSecretKey().map(key -> {
            byte[] keyBytes = Decoders.BASE64.decode(key);
            return Keys.hmacShaKeyFor(keyBytes);
        });
    }

    private static String getErrorMsg(Throwable e) {
        System.out.println("====> JwtUtilsImpl.getErrorMsg(): " + e.getMessage());
        int indexOfColon = e.getMessage().indexOf(':');
        return (indexOfColon < 0) ?
            e.getMessage() : e.getMessage().substring(0, indexOfColon);
    }
}
