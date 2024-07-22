/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common;

import java.util.Collections;
import java.util.List;

import com.richarddklein.shorturlcommonlibrary.exception.InvalidJwtException;
import com.richarddklein.shorturlcommonlibrary.exception.MissingAuthorizationHeaderException;
import com.richarddklein.shorturlcommonlibrary.security.dto.UsernameAndRole;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common.JwtTokenAuthenticationConverter;
import com.richarddklein.shorturlcommonlibrary.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtTokenAuthenticationConverterImpl implements JwtTokenAuthenticationConverter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Mono.error(new MissingAuthorizationHeaderException(
                    "The request does not contain a Bearer Token authorization header"));
        }

        String jwtToken = authorizationHeader.substring("Bearer ".length()).trim();

        return jwtUtils.extractUsernameAndRoleFromToken(jwtToken).map(usernameAndRole -> {
            String username = usernameAndRole.getUsername();
            String role = usernameAndRole.getRole();

            List<SimpleGrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority(role));
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            return (Authentication)authenticationToken;
        }).onErrorResume(e -> {
            System.out.println("====> " + e.getMessage());
            int indexOfColon = e.getMessage().indexOf(':');
            String errorMsg = (indexOfColon < 0) ?
                    e.getMessage() : e.getMessage().substring(0, indexOfColon);
            return Mono.error(new InvalidJwtException(errorMsg));
        });
    }
}
