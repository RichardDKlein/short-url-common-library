/**
 * The Short URL WebClient Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlwebclientlibrary.shorturlreservationservice.dto;

/**
 * Class defining the HTTP Response sent by the Short URL Reservation service
 * in response to a request to reserve any available short URL.
 */
public class ReserveAnyShortUrlApiResponse {
    private Status status;
    private ShortUrlReservation shortUrlReservation;

    public Status getStatus() {
        return status;
    }

    public ShortUrlReservation getShortUrlReservation() {
        return shortUrlReservation;
    }
}
