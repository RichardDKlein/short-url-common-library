/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.cookieauthentication;

import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatus;
import com.richarddklein.shorturlcommonlibrary.security.exception.InvalidJwtException;
import com.richarddklein.shorturlcommonlibrary.security.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

import static com.richarddklein.shorturlcommonlibrary.security.cookieauthentication.CookieAuthenticationConverterImpl.AUTH_TOKEN;

public class CookieAuthenticationFailureHandlerImpl implements CookieAuthenticationFailureHandler {
    @Autowired
    private HttpUtils httpUtils;

    @Override
    public Mono<Void> onAuthenticationFailure(
            WebFilterExchange webFilterExchange,
            AuthenticationException exception) {

        SecurityStatus status = getSecurityStatus(exception);
        return httpUtils.generateResponse(
                webFilterExchange, exception, status, AUTH_TOKEN);
    }

    private SecurityStatus getSecurityStatus(AuthenticationException exception) {
        SecurityStatus status = null;
        if (exception instanceof InvalidJwtException) {
            status = SecurityStatus.INVALID_JWT_EXCEPTION;
        }
        return status;
    }
}
