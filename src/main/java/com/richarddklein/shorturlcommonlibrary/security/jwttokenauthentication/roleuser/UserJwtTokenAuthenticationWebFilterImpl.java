/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleuser;

import java.util.Arrays;
import java.util.List;

import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common.JwtTokenAuthenticationConverter;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common.JwtTokenAuthenticationFailureHandler;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

public class UserJwtTokenAuthenticationWebFilterImpl extends UserJwtTokenAuthenticationWebFilter {
    public UserJwtTokenAuthenticationWebFilterImpl(
            UserJwtTokenAuthenticationManager userJwtTokenAuthenticationManager,
            JwtTokenAuthenticationConverter jwtTokenAuthenticationConverter,
            JwtTokenAuthenticationFailureHandler jwtTokenAuthenticationFailureHandler) {

        super(userJwtTokenAuthenticationManager);

//        setServerAuthenticationConverter(jwtTokenAuthenticationConverter);
//        setAuthenticationFailureHandler(jwtTokenAuthenticationFailureHandler);

        // Matchers cannot be empty. Spring will throw an exception during initialization.ß
//        List<ServerWebExchangeMatcher> matchers = Arrays.asList(
//        );
//
//        setRequiresAuthenticationMatcher(new OrServerWebExchangeMatcher(matchers));
    }
}
