/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

public class JwtTokenAuthenticationWebFilterImpl extends JwtTokenAuthenticationWebFilter {
    public JwtTokenAuthenticationWebFilterImpl(
            JwtTokenAuthenticationManager jwtTokenAuthenticationManager,
            JwtTokenAuthenticationConverter jwtTokenAuthenticationConverter,
            JwtTokenAuthenticationFailureHandler jwtTokenAuthenticationFailureHandler) {

        super(jwtTokenAuthenticationManager);

        setServerAuthenticationConverter(jwtTokenAuthenticationConverter);
        setAuthenticationFailureHandler(jwtTokenAuthenticationFailureHandler);


        List<ServerWebExchangeMatcher> matchers = Arrays.asList(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET,
                    "/validate", "/shorturl/users/validate",
                    "/details", "/shorturl/users/details"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.PATCH,
                    "/changepassword", "/shorturl/users/changepassword"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE,
                        "/delete", "/shorturl/users/delete")
        );

        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
