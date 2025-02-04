/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.dto;

/**
 * The Security Status.
 *
 * An enumerated type describing the various possible security-related
 * statuses that can be returned in response to a client request.
 */
public enum SecurityStatus {
    EXPIRED_JWT_EXCEPTION,
    INVALID_ADMIN_CREDENTIALS,
    INVALID_JWT_EXCEPTION,
    MISSING_BASIC_AUTHORIZATION_HEADER,
    MISSING_BEARER_TOKEN_AUTHORIZATION_HEADER,
    MUST_BE_ADMIN,
    MUST_BE_LOGGED_OUT,
}
