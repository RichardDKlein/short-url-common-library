/**
 * The Short URL User Service
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.exception;

import org.springframework.security.core.AuthenticationException;

public class MustBeAdminException extends AuthenticationException {
    public MustBeAdminException(String msg) {
        super(msg);
    }
}
