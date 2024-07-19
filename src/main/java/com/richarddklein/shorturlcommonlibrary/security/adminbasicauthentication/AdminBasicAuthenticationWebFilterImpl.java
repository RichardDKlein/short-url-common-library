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
                        "/adminjwt", "/shorturl/users/adminjwt"),
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST,
                        "/dbinit", "/shorturl/users/dbinit", "/shorturl/reservations/dbinit")
        );

        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
