/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminbasicauthentication;

import com.richarddklein.shorturlcommonlibrary.security.exception.MissingAuthorizationHeaderException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AdminBasicAuthenticationConverterImpl implements AdminBasicAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authorizationHeader =
                exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            String message = "The request does not contain a Basic authorization header";
            System.out.println("====> AdminBasicAuthenticationConverterImpl: " + message);
            return Mono.error(new MissingAuthorizationHeaderException(message));
        }
        String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Credentials);
        String[] credentials = new String(decodedBytes).split(":", 2);
        String username = credentials[0];
        String password = credentials[1];
        return Mono.just(new UsernamePasswordAuthenticationToken(username, password));
    }
}
