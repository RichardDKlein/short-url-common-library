/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleadmin;

import java.util.Arrays;
import java.util.List;

import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common.JwtTokenAuthenticationConverter;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common.JwtTokenAuthenticationFailureHandler;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

public class AdminJwtTokenAuthenticationWebFilterImpl extends AdminJwtTokenAuthenticationWebFilter {
    public AdminJwtTokenAuthenticationWebFilterImpl(
            AdminJwtTokenAuthenticationManager adminJwtTokenAuthenticationManager,
            JwtTokenAuthenticationConverter jwtTokenAuthenticationConverter,
            JwtTokenAuthenticationFailureHandler jwtTokenAuthenticationFailureHandler) {

        super(adminJwtTokenAuthenticationManager);

        setServerAuthenticationConverter(jwtTokenAuthenticationConverter);
        setAuthenticationFailureHandler(jwtTokenAuthenticationFailureHandler);

        List<ServerWebExchangeMatcher> matchers = Arrays.asList(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST,
                        "/signup", "/shorturl/users/signup",
                        "/login", "/shorturl/users/login"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET,
                        "/all", "/shorturl/users/all", "/shorturl/reservations/all",
                        "/specific/**", "/shorturl/reservations/specific/**",
                        "/details", "/shorturl/users/details"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.PATCH,
                        "/changepassword", "/shorturl/users/changepassword",
                        "/reserve/any", "/shorturl/reservations/reserve/any",
                        "/reserve/specific/**", "/shorturl/reservations/reserve/specific/**",
                        "/reserve/all", "/shorturl/reservations/reserve/all",
                        "/cancel/specific/**", "/shorturl/reservations/cancel/specific/**",
                        "/cancel/all", "/shorturl/reservations/cancel/all"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE,
                        "/specific", "/shorturl/users/specific",
                        "/all", "/shorturl/users/all")
        );

        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
