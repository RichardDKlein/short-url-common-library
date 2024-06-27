/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleadmin;

import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common.JwtTokenAuthenticationWebFilter;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

public class AdminJwtTokenAuthenticationWebFilter extends JwtTokenAuthenticationWebFilter {
    public AdminJwtTokenAuthenticationWebFilter(
            AdminJwtTokenAuthenticationManager adminJwtTokenAuthenticationManager) {

        super(adminJwtTokenAuthenticationManager);
    }
}
