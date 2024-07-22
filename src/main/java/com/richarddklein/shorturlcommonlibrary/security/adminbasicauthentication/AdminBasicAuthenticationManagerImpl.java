/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.adminbasicauthentication;

import com.richarddklein.shorturlcommonlibrary.aws.ParameterStoreReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class AdminBasicAuthenticationManagerImpl implements AdminBasicAuthenticationManager {
    @Autowired
    ParameterStoreReader parameterStoreReader;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return parameterStoreReader.getAdminUsername().flatMap(adminUsername ->
                parameterStoreReader.getAdminPassword().flatMap(adminPassword -> {

            if (authentication.getPrincipal().equals(adminUsername) &&
                    authentication.getCredentials().equals(adminPassword)) {
                    System.out.print("====> AdminBasicAuthenticationManagerImpl: " +
                            "admin credentials are correct\n");
                    return Mono.just(authentication);
            } else {
                    return Mono.error(new BadCredentialsException(
                            "The Authorization header does not contain valid Admin credentials"));
            }
        }));
    }
}
