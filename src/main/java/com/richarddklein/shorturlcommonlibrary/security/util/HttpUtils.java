/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import reactor.core.publisher.Mono;

public interface HttpUtils {
    Mono<Void> generateResponse(
        WebFilterExchange webFilterExchange,
        AuthenticationException exception,
        SecurityStatus status,
        String cookieToDelete);
}
