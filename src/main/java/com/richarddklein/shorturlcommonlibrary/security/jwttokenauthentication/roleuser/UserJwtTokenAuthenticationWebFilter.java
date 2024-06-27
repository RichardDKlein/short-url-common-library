/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleuser;

import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common.JwtTokenAuthenticationWebFilter;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

public class UserJwtTokenAuthenticationWebFilter extends JwtTokenAuthenticationWebFilter {
    public UserJwtTokenAuthenticationWebFilter(
            UserJwtTokenAuthenticationManager userJwtTokenAuthenticationManager) {

        super(userJwtTokenAuthenticationManager);
    }
}
