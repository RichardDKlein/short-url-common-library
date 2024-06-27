/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminauthentication;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

public class AdminAuthenticationWebFilterImpl extends AdminAuthenticationWebFilter {
    public AdminAuthenticationWebFilterImpl(
            AdminAuthenticationManager adminAuthenticationManager,
            AdminAuthenticationConverter adminAuthenticationConverter,
            AdminAuthenticationFailureHandler adminAuthenticationFailureHandler) {

        super(adminAuthenticationManager);

        setServerAuthenticationConverter(adminAuthenticationConverter);
        setAuthenticationFailureHandler(adminAuthenticationFailureHandler);

        List<ServerWebExchangeMatcher> matchers = Arrays.asList(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST,
                        "/dbinit", "/shorturl/users/dbinit")
        );

        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
