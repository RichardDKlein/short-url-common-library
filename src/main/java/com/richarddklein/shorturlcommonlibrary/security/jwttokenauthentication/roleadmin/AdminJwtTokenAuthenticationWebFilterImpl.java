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
                        "**/signup",
                        "**/login"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET,
                        "**/specific/**",
                        "**/all",
                        "**/details"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.PATCH,
                        "**/reserve/**",
                        "**/cancel/**",
                        "**/changepassword"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE,
                        "**/specific",
                        "**/all")
        );
        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
