/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleuser;

import com.richarddklein.shorturlcommonlibrary.exception.MustBeAdminException;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleadmin.AdminJwtTokenAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import reactor.core.publisher.Mono;

public class UserJwtTokenAuthenticationManagerImpl implements UserJwtTokenAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication);
    }
}
