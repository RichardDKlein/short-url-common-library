/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.nocookieauthentication;

import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatus;
import com.richarddklein.shorturlcommonlibrary.security.exception.MustBeLoggedOutException;
import com.richarddklein.shorturlcommonlibrary.security.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

public class NoCookieAuthenticationFailureHandlerImpl implements NoCookieAuthenticationFailureHandler {
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
        if (exception instanceof MustBeLoggedOutException) {
            status = SecurityStatus.MUST_BE_LOGGED_OUT;
        }
        return status;
    }
}
