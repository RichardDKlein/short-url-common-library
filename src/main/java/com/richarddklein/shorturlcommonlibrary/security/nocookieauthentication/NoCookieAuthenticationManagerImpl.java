/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.nocookieauthentication;

import com.richarddklein.shorturlcommonlibrary.security.exception.MustBeLoggedOutException;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class NoCookieAuthenticationManagerImpl implements NoCookieAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String message = "Must be logged out to perform this operation";
        System.out.println("====> NoCookieAuthenticationManagerImpl: " + message);
        return Mono.error(new MustBeLoggedOutException(message));
    }
}
