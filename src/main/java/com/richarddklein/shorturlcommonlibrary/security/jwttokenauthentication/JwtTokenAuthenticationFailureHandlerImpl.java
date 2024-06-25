/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richarddklein.shorturlcommonlibrary.exception.InvalidJwtException;
import com.richarddklein.shorturlcommonlibrary.exception.MissingAuthorizationHeaderException;
import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatus;
import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatusResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

public class JwtTokenAuthenticationFailureHandlerImpl implements JwtTokenAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange,
                                              AuthenticationException exception) {
        SecurityStatus status = null;
        String message = null;

        if (exception instanceof MissingAuthorizationHeaderException) {
            status = SecurityStatus.MISSING_BEARER_TOKEN_AUTHORIZATION_HEADER;
            message = "The request does not contain a Bearer Token authorization header";
        } else if (exception instanceof InvalidJwtException) {
            status = SecurityStatus.INVALID_JWT_EXCEPTION;
            message = exception.getMessage();
        }

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        SecurityStatusResponse errorResponse = new SecurityStatusResponse(status, message);
        byte[] responseBytes;
        try {
            responseBytes = new ObjectMapper().writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(responseBytes);

        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }
}
