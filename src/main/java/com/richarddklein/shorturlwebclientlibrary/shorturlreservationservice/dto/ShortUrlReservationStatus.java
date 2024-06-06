/**
 * The Short URL WebClient Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlwebclientlibrary.shorturlreservationservice.dto;

/**
 * The Short URL Reservation Status.
 *
 * An enumerated type describing the various possible statuses
 * that can be returned by the Short URL Reservation service
 * in response to a web client request.
 */
public enum ShortUrlReservationStatus {
    // IMPORTANT: Keep this in sync with the ShortUrlReservationStatus
    // enum defined in the short-url-reservation-service project.
    // ---------------------------------------------------------------
    SUCCESS,
    SHORT_URL_NOT_FOUND,
    SHORT_URL_FOUND_BUT_NOT_AVAILABLE,
    SHORT_URL_FOUND_BUT_NOT_RESERVED,
    NO_SHORT_URL_IS_AVAILABLE,
    NOT_ON_LOCAL_MACHINE,
    UNKNOWN,
}
