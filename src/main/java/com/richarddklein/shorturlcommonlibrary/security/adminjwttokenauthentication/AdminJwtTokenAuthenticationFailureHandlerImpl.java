/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminjwttokenauthentication;

import com.richarddklein.shorturlcommonlibrary.security.exception.InvalidJwtException;
import com.richarddklein.shorturlcommonlibrary.security.exception.MissingAuthorizationHeaderException;
import com.richarddklein.shorturlcommonlibrary.security.exception.MustBeAdminException;
import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatus;
import com.richarddklein.shorturlcommonlibrary.security.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

public class AdminJwtTokenAuthenticationFailureHandlerImpl implements AdminJwtTokenAuthenticationFailureHandler {
    @Autowired
    private HttpUtils httpUtils;

    @Override
    public Mono<Void> onAuthenticationFailure(
            WebFilterExchange webFilterExchange,
            AuthenticationException exception) {

        SecurityStatus status = getSecurityStatus(exception);
        return httpUtils.generateResponse(
                webFilterExchange, exception, status, null);
    }

    private SecurityStatus getSecurityStatus(AuthenticationException exception) {
        SecurityStatus status = null;
        if (exception instanceof MissingAuthorizationHeaderException) {
            status = SecurityStatus.MISSING_BEARER_TOKEN_AUTHORIZATION_HEADER;
        } else if (exception instanceof InvalidJwtException) {
            status = SecurityStatus.INVALID_JWT_EXCEPTION;
        } else if (exception instanceof MustBeAdminException) {
            status = SecurityStatus.MUST_BE_ADMIN;
        }
        return status;
    }
}
