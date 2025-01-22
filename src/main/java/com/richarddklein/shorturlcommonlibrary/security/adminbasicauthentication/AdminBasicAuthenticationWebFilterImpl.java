/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminbasicauthentication;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

public class AdminBasicAuthenticationWebFilterImpl extends AdminBasicAuthenticationWebFilter {
    public AdminBasicAuthenticationWebFilterImpl(
            AdminBasicAuthenticationManager adminBasicAuthenticationManager,
            AdminBasicAuthenticationConverter adminBasicAuthenticationConverter,
            AdminBasicAuthenticationFailureHandler adminBasicAuthenticationFailureHandler) {

        super(adminBasicAuthenticationManager);

        setServerAuthenticationConverter(adminBasicAuthenticationConverter);
        setAuthenticationFailureHandler(adminBasicAuthenticationFailureHandler);

        List<ServerWebExchangeMatcher> matchers = Arrays.asList(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET,
                        "/admin-jwt", "/short-url/users/admin-jwt"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST,
                        "/initialize-repository",
                        "/short-url/users/initialize-repository",
                        "/short-url/reservations/initialize-repository",
                        "/short-url/mappings/initialize-repository"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.PUT,
                        "/jwt-minutes-to-live", "/short-url/users/jwt-minutes-to-live")
        );

        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
