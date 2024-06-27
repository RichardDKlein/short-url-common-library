/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleadmin;

import com.richarddklein.shorturlcommonlibrary.exception.MustBeAdminException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import reactor.core.publisher.Mono;

public class AdminJwtTokenAuthenticationManagerImpl implements AdminJwtTokenAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String role = "";
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            role = authority.getAuthority();
        }
        if (role.equals("ADMIN")) {
            return Mono.just(authentication);
        } else {
            return Mono.error(new MustBeAdminException("Must be an admin to perform this operation"));
        }
    }
}
