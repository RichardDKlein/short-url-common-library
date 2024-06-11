/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

public interface JwtUtils {
    Mono<String> generateToken(Mono<String> usernameMono, Mono<String> roleMono);
    Claims getClaimsFromToken(String jwtToken);
}
