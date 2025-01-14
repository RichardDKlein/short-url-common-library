/**
 * The Short URL User Service
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ExpiredJwtException extends AuthenticationException {
    public ExpiredJwtException(String msg) {
        super(msg);
    }
}
