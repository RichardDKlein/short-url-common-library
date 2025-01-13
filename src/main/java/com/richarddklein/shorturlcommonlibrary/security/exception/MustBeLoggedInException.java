/**
 * The Short URL User Service
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.exception;

import org.springframework.security.core.AuthenticationException;

public class MustBeLoggedInException extends AuthenticationException {
    public MustBeLoggedInException(String msg) {
        super(msg);
    }
}
