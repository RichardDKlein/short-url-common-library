/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminjwttokenauthentication;

import com.richarddklein.shorturlcommonlibrary.security.exception.MustBeAdminException;
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
            String message = "Must be an admin to perform this operation";
            System.out.println("====> AdminJwtTokenAuthenticationManagerImpl: " + message);
            return Mono.error(new MustBeAdminException(message));
        }
    }
}
