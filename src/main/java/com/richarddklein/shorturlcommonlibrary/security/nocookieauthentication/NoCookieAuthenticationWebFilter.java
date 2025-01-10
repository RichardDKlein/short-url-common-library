/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.nocookieauthentication;

import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

public class NoCookieAuthenticationWebFilter extends AuthenticationWebFilter {
    public NoCookieAuthenticationWebFilter(
            NoCookieAuthenticationManager noCookieAuthenticationManager) {

        super(noCookieAuthenticationManager);
    }
}
