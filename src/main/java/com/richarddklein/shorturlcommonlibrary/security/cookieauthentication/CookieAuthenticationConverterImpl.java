/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.cookieauthentication;

import com.richarddklein.shorturlcommonlibrary.security.exception.MustBeLoggedInException;
import com.richarddklein.shorturlcommonlibrary.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CookieAuthenticationConverterImpl implements CookieAuthenticationConverter {
    private static final String AUTH_TOKEN = "auth_token";

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        HttpCookie authCookie = cookies.getFirst(AUTH_TOKEN);

        if (authCookie == null) {
            String message = "Must be logged in to perform this operation";
            System.out.println("====> CookieAuthenticationConverterImpl: " + message);
            return Mono.error(new MustBeLoggedInException(message));
        }
        String jwtToken = authCookie.getValue();
        return jwtUtils.authenticateToken(jwtToken);
    }
}
