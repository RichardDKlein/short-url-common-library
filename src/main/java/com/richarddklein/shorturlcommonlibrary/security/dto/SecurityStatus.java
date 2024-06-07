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
    SUCCESS,
    INVALID_ADMIN_CREDENTIALS,
    INVALID_JWT_EXCEPTION,
    MISSING_AUTHORIZATION_HEADER,
    MISSING_PASSWORD,
}
