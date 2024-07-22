/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import com.richarddklein.shorturlcommonlibrary.security.dto.UsernameAndRole;
import reactor.core.publisher.Mono;

public interface JwtUtils {
    Mono<String> generateToken(UsernameAndRole usernameAndRole);
    Mono<UsernameAndRole> extractUsernameAndRoleFromToken(String jwtToken);
}
