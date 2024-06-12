/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class JwtTokenAuthenticationManagerImpl implements JwtTokenAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication);
    }
}
