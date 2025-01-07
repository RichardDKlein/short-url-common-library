/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.environment;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface HostUtils {
    Boolean isRunningLocally(ServerHttpRequest request);
    Mono<String> getDomain();
    Mono<String> getShortUrlUserServiceBaseUrl(ServerHttpRequest request);
    Mono<String> getShortUrlReservationServiceBaseUrl(ServerHttpRequest request);
    Mono<String> getShortUrlMappingServiceBaseUrl(ServerHttpRequest request);
}
