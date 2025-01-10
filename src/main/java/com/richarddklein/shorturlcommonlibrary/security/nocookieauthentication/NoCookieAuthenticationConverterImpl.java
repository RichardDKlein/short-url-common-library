/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.nocookieauthentication;

import com.richarddklein.shorturlcommonlibrary.security.exception.MustBeLoggedOutException;
import org.springframework.http.HttpCookie;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class NoCookieAuthenticationConverterImpl implements NoCookieAuthenticationConverter {
    private static final String AUTH_TOKEN = "auth_token";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        HttpCookie authCookie = cookies.getFirst(AUTH_TOKEN);

        if (authCookie != null) {
            String message = "Must be logged out to perform this operation";
            System.out.println("====> NoCookieAuthenticationConverterImpl: " + message);
            return Mono.error(new MustBeLoggedOutException(message));
        }
        return Mono.empty();
    }
}
