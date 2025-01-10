/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.nocookieauthentication;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

public class NoCookieAuthenticationWebFilterImpl extends NoCookieAuthenticationWebFilter {
    public NoCookieAuthenticationWebFilterImpl(
            NoCookieAuthenticationManager noCookieAuthenticationManager,
            NoCookieAuthenticationConverter noCookieAuthenticationConverter,
            NoCookieAuthenticationFailureHandler noCookieAuthenticationFailureHandler) {

        super(noCookieAuthenticationManager);

        setServerAuthenticationConverter(noCookieAuthenticationConverter);
        setAuthenticationFailureHandler(noCookieAuthenticationFailureHandler);

        List<ServerWebExchangeMatcher> matchers = Arrays.asList(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST,
                        "/signup-user", "/short-url/signup-user",
                        "/login-user", "/short-url/login-user")
        );

        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
