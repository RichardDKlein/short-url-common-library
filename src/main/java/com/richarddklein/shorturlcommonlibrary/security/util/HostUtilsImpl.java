/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import java.util.Objects;

import org.springframework.http.server.reactive.ServerHttpRequest;

public class HostUtilsImpl implements HostUtils {
    @Override
    public Boolean isRunningLocally(ServerHttpRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        return request.getURI().getHost().equals("localhost");
    }
}
