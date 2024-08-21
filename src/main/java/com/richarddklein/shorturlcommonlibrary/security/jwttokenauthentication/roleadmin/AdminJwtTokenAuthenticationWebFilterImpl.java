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
                        "/signup", "/short-url/users/signup",
                        "/login", "/short-url/users/login",
                        "/create-mapping", "/short-url/mappings/create-mapping"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET,
                        "/all",
                            "/short-url/users/all",
                            "/short-url/reservations/all",
                        "/get-mappings",
                            "/short-url/mappings/get-mappings",
                        "/specific/**",
                            "/short-url/users/specific/**",
                            "/short-url/reservations/specific/**"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.PATCH,
                        "/change-long-url", "/short-url/mappings/change-long-url",
                        "/change-password", "/short-url/users/change-password",
                        "/reserve/any", "/short-url/reservations/reserve/any",
                        "/reserve/specific/**", "/short-url/reservations/reserve/specific/**",
                        "/reserve/all", "/short-url/reservations/reserve/all",
                        "/cancel/specific/**", "/short-url/reservations/cancel/specific/**",
                        "/cancel/all", "/short-url/reservations/cancel/all"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE,
                        "/specific/**",
                            "/short-url/users/specific/**",
                        "/all",
                            "/short-url/users/all",
                        "/delete-mappings",
                            "/short-url/mappings/delete-mappings")
        );

        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
