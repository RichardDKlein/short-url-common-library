/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.shorturluserservice.dto;

@SuppressWarnings("unused")
public class JwtMinutesToLiveTest {
    private int jwtMinutesToLiveTest;

    public JwtMinutesToLiveTest() {
    }

    public JwtMinutesToLiveTest(int jwtMinutesToLiveTest) {
        this.jwtMinutesToLiveTest = jwtMinutesToLiveTest;
    }

    public int getJwtMinutesToLiveTest() {
        return jwtMinutesToLiveTest;
    }

    public void setJwtMinutesToLiveTest(int jwtMinutesToLiveTest) {
        this.jwtMinutesToLiveTest = jwtMinutesToLiveTest;
    }

    @Override
    public String toString() {
        return "JwtMinutesToLiveTest{" +
                "jwtMinutesToLiveTest=" + jwtMinutesToLiveTest +
                '}';
    }
}
