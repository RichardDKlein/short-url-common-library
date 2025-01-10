/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.shorturluserservice.dto;

import com.richarddklein.shorturlcommonlibrary.service.shorturluserservice.entity.ShortUrlUser;
import com.richarddklein.shorturlcommonlibrary.service.status.Status;

@SuppressWarnings("unused")
public class StatusAndShortUrlUser {
    private Status status;
    private ShortUrlUser shortUrlUser;

    public StatusAndShortUrlUser() {
    }

    public StatusAndShortUrlUser(Status status, ShortUrlUser shortUrlUser) {
        this.status = status;
        this.shortUrlUser = shortUrlUser;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ShortUrlUser getShortUrlUser() {
        return shortUrlUser;
    }

    public void setShortUrlUser(ShortUrlUser shortUrlUser) {
        this.shortUrlUser = shortUrlUser;
    }
}
