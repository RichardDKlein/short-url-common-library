/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatus;
import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatusResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

public class HttpUtilsImpl implements HttpUtils {

    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------

    @Override
    public Mono<Void> generateResponse(
            WebFilterExchange webFilterExchange,
            AuthenticationException exception,
            SecurityStatus status,
            String cookieToDelete) {

        System.out.println("====> HttpUtilsImpl.generateResponse(): " + exception.getMessage());

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        HttpHeaders responseHeaders = response.getHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (cookieToDelete != null) {
            deleteCookie(responseHeaders, cookieToDelete);
        }

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

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private void deleteCookie(HttpHeaders responseHeaders, String cookieToDelete) {
        responseHeaders.add(HttpHeaders.SET_COOKIE,
            ResponseCookie.from(cookieToDelete, "")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .build()
                .toString());
    }
}
