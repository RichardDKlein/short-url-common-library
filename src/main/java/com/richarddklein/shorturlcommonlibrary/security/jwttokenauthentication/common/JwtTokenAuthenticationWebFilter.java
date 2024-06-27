/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

public class JwtTokenAuthenticationWebFilter extends AuthenticationWebFilter {
    public JwtTokenAuthenticationWebFilter(
            JwtTokenAuthenticationManager jwtTokenAuthenticationManager) {

        super(jwtTokenAuthenticationManager);
    }
}
