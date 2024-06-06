/**
 * The Short URL WebClient Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlwebclientlibrary.shorturlreservationservice.dto;

/**
 * Class defining the HTTP Response sent by the Short URL Reservation service
 * in response to a request to reserve a specific short URL.
 */
public class ReserveSpecificShortUrlApiResponse {
    private Status status;

    public Status getStatus() {
        return status;
    }
}
