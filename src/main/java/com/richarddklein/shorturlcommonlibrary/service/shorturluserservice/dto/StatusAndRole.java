/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.shorturluserservice.dto;

import com.richarddklein.shorturlcommonlibrary.service.status.Status;

@SuppressWarnings("unused")
public class StatusAndRole {
    private Status status;
    private String role;

    public StatusAndRole() {
    }

    public StatusAndRole(Status status, String role) {
        this.status = status;
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "StatusAndRole{" +
                "status=" + status +
                ", role='" + role + '\'' +
                '}';
    }
}
