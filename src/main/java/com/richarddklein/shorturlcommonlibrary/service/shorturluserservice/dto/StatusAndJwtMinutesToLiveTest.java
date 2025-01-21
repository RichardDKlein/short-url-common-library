/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.shorturluserservice.dto;

import com.richarddklein.shorturlcommonlibrary.service.status.Status;

@SuppressWarnings("unused")
public class StatusAndJwtMinutesToLiveTest {
    private Status status;
    private int jwtMinutesToLiveTest;

    public StatusAndJwtMinutesToLiveTest() {
    }

    public StatusAndJwtMinutesToLiveTest(Status status, int jwtMinutesToLiveTest) {
        this.status = status;
        this.jwtMinutesToLiveTest = jwtMinutesToLiveTest;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getJwtMinutesToLiveTest() {
        return jwtMinutesToLiveTest;
    }

    public void setJwtMinutesToLiveTest(int jwtMinutesToLiveTest) {
        this.jwtMinutesToLiveTest = jwtMinutesToLiveTest;
    }

    @Override
    public String toString() {
        return "StatusAndJwtMinutesToLiveTest{" +
                "status=" + status +
                ", jwtMinutesToLiveTest='" + jwtMinutesToLiveTest +
                '}';
    }
}
