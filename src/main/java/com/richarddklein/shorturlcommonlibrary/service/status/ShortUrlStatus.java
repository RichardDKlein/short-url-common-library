/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.status;

public enum ShortUrlStatus {
    SUCCESS,

    // Please keep the following in alphabetical order
    // ------------------------------------------------
    BAD_LONG_URL_SYNTAX,
    MISSING_LONG_URL,
    MISSING_NEW_PASSWORD,
    MISSING_OLD_PASSWORD,
    MISSING_PASSWORD,
    MISSING_SHORT_URL,
    MISSING_USERNAME,
    NO_SHORT_URLS_ARE_AVAILABLE,
    NO_SUCH_SHORT_URL,
    NO_SUCH_USER,
    NOT_LOGGED_IN,
    NOT_ON_LOCAL_MACHINE,
    NOT_TEST_PROFILE,
    SHORT_URL_ALREADY_TAKEN,
    SHORT_URL_NOT_FOUND,
    SHORT_URL_NOT_RESERVED,
    UNKNOWN_ERROR,
    USER_ALREADY_EXISTS,
    WRONG_PASSWORD,
    WRONG_USER,
}
