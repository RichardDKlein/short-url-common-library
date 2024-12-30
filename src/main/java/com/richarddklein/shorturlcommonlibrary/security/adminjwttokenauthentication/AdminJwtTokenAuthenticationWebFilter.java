/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminjwttokenauthentication;

import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

public class AdminJwtTokenAuthenticationWebFilter extends AuthenticationWebFilter {
    public AdminJwtTokenAuthenticationWebFilter(
            AdminJwtTokenAuthenticationManager adminJwtTokenAuthenticationManager) {

        super(adminJwtTokenAuthenticationManager);
    }
}
