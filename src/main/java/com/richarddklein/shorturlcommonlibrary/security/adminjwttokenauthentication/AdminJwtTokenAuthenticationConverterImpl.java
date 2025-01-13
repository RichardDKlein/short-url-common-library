/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminjwttokenauthentication;

import com.richarddklein.shorturlcommonlibrary.security.exception.MissingAuthorizationHeaderException;
import com.richarddklein.shorturlcommonlibrary.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AdminJwtTokenAuthenticationConverterImpl implements AdminJwtTokenAuthenticationConverter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            String message = "The request does not contain a Bearer Token authorization header";
            System.out.println("====> AdminJwtTokenAuthenticationConverterImpl: " + message);
            return Mono.error(new MissingAuthorizationHeaderException(message));
        }
        String jwtToken = authorizationHeader.substring("Bearer ".length()).trim();
        return jwtUtils.authenticateToken(jwtToken);
    }
}
