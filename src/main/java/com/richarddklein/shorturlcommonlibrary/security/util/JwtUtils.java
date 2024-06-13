/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import com.richarddklein.shorturlcommonlibrary.security.dto.UsernameAndRole;
import io.jsonwebtoken.Claims;

public interface JwtUtils {
    String generateToken(UsernameAndRole shortUrlUser);
    UsernameAndRole extractUsernameAndRoleFromToken(String jwtToken);
}
