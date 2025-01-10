/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.shorturlreservationservice.dto;

import java.util.List;

import com.richarddklein.shorturlcommonlibrary.service.shorturlreservationservice.entity.ShortUrlReservation;
import com.richarddklein.shorturlcommonlibrary.service.status.Status;

@SuppressWarnings("unused")
public class StatusAndShortUrlReservationArray {
    private Status status;
    private List<ShortUrlReservation> shortUrlReservations;

    public StatusAndShortUrlReservationArray() {
    }

    public StatusAndShortUrlReservationArray(
            Status status,
            List<ShortUrlReservation> shortUrlReservations) {

        this.status = status;
        this.shortUrlReservations = shortUrlReservations;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ShortUrlReservation> getShortUrlReservations() {
        return shortUrlReservations;
    }

    public void setShortUrlReservations(List<ShortUrlReservation> shortUrlReservations) {
        this.shortUrlReservations = shortUrlReservations;
    }
}
