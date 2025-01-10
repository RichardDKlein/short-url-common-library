/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.status;

public class Status {
    private ShortUrlStatus status;
    private String message;

    public Status(ShortUrlStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status(ShortUrlStatus status) {
        this.status = status;
    }

    public ShortUrlStatus getStatus() {
        return status;
    }

    public void setStatus(ShortUrlStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
