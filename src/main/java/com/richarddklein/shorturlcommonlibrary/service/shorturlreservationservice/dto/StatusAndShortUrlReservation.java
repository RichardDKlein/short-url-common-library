/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.shorturlreservationservice.dto;

import com.richarddklein.shorturlcommonlibrary.service.shorturlreservationservice.entity.ShortUrlReservation;
import com.richarddklein.shorturlcommonlibrary.service.status.Status;

@SuppressWarnings("unused")
public class StatusAndShortUrlReservation {
    private Status status;
    private ShortUrlReservation shortUrlReservation;

    public StatusAndShortUrlReservation() {
    }

    public StatusAndShortUrlReservation(
            Status status,
            ShortUrlReservation shortUrlReservation) {

        this.status = status;
        this.shortUrlReservation = shortUrlReservation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ShortUrlReservation getShortUrlReservation() {
        return shortUrlReservation;
    }

    public void setShortUrlReservation(ShortUrlReservation shortUrlReservation) {
        this.shortUrlReservation = shortUrlReservation;
    }
}
