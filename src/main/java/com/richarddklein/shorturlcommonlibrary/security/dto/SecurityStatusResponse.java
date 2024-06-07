/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.dto;

import com.richarddklein.shorturlcommonlibrary.security.dto.SecurityStatus;

/**
 * Class defining an HTTP Response containing a security status
 * code/message only.
 */
public class SecurityStatusResponse {
    private SecurityStatus status;
    private String message;

    /**
     * General constructor.
     *
     * @param status The security status code to be embedded in the HTTP Response.
     * @param message The security status message to be embedded in the HTTP Response.
     */
    public SecurityStatusResponse(SecurityStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public SecurityStatus getStatus() {
        return status;
    }

    public void setStatus(SecurityStatus status) {
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
        return "StatusResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
