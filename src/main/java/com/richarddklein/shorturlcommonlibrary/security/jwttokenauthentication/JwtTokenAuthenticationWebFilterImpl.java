/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

public class JwtTokenAuthenticationWebFilterImpl extends JwtTokenAuthenticationWebFilter {
    public JwtTokenAuthenticationWebFilterImpl(
            JwtTokenAuthenticationManager jwtTokenAuthenticationManager,
            JwtTokenAuthenticationConverter jwtTokenAuthenticationConverter,
            JwtTokenAuthenticationFailureHandler jwtTokenAuthenticationFailureHandler) {

        super(jwtTokenAuthenticationManager);

        setServerAuthenticationConverter(jwtTokenAuthenticationConverter);
        setAuthenticationFailureHandler(jwtTokenAuthenticationFailureHandler);

        setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers(
                        HttpMethod.GET,
                        "/validate", "/shorturl/users/validate",
                        "/details", "/shorturl/users/details",
                        "/changepassword", "/shorturl/users/changepassword"));
    }
}
