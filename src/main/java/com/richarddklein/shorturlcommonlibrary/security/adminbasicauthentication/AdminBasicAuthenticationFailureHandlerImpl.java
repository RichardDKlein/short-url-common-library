/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminbasicauthentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richarddklein.shorturlcommonlibrary.exception.MissingAuthorizationHeaderException;
import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatus;
import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatusResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

public class AdminBasicAuthenticationFailureHandlerImpl implements AdminBasicAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange,
                                              AuthenticationException exception) {
        SecurityStatus status = null;
        String message = null;

        if (exception instanceof MissingAuthorizationHeaderException) {
            status = SecurityStatus.MISSING_BASIC_AUTHORIZATION_HEADER;
        } else if (exception instanceof BadCredentialsException) {
            status = SecurityStatus.INVALID_ADMIN_CREDENTIALS;
        }

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        SecurityStatusResponse errorResponse =
                new SecurityStatusResponse(status, exception.getMessage());
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
