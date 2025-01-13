/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import com.richarddklein.shorturlcommonlibrary.security.dto.UsernameAndRole;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface JwtUtils {
    Mono<String> generateToken(UsernameAndRole usernameAndRole);
    Mono<Authentication> authenticateToken(String token);
    Mono<Boolean> isExpired(String token);
    Mono<UsernameAndRole> extractUsernameAndRoleFromToken(String token);
}
