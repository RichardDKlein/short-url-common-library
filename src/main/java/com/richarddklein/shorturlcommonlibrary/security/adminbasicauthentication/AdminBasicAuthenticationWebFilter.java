/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminbasicauthentication;

import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

public class AdminBasicAuthenticationWebFilter extends AuthenticationWebFilter {
    public AdminBasicAuthenticationWebFilter(
            AdminBasicAuthenticationManager adminBasicAuthenticationManager) {

        super(adminBasicAuthenticationManager);
    }
}
