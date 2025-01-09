/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.environment;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface HostUtils {
    Boolean isRunningLocally();
    Mono<String> getDomain();
    Mono<String> getShortUrlUserServiceBaseUrl();
    Mono<String> getShortUrlMappingServiceBaseUrl();
    Mono<String> getShortUrlReservationServiceBaseUrl();
}
