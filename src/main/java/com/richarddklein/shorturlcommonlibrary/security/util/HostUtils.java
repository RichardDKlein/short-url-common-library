/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import org.springframework.http.server.reactive.ServerHttpRequest;

public interface HostUtils {
    Boolean isRunningLocally(ServerHttpRequest request);
}
