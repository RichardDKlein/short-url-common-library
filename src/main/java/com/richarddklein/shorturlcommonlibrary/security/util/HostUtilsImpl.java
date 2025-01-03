/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security.util;

import java.util.Objects;

import com.richarddklein.shorturlcommonlibrary.aws.ParameterStoreAccessor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public class HostUtilsImpl implements HostUtils {
    private final ParameterStoreAccessor parameterStoreAccessor;

    public HostUtilsImpl(ParameterStoreAccessor parameterStoreAccessor) {
        this.parameterStoreAccessor = parameterStoreAccessor;
    }

    @Override
    public Boolean isRunningLocally(ServerHttpRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        return request.getURI().getHost().equals("localhost");
    }

    @Override
    public Mono<String> getShortUrlUserServiceBaseUrl(ServerHttpRequest request) {
        return isRunningLocally(request)
            ? parameterStoreAccessor.getShortUrlUserServiceBaseUrlLocal()
            : parameterStoreAccessor.getShortUrlUserServiceBaseUrlAws();
    }

    @Override
    public Mono<String> getShortUrlReservationServiceBaseUrl(ServerHttpRequest request) {
        return isRunningLocally(request)
            ? parameterStoreAccessor.getShortUrlReservationServiceBaseUrlLocal()
            : parameterStoreAccessor.getShortUrlReservationServiceBaseUrlAws();
    }

    @Override
    public Mono<String> getShortUrlMappingServiceBaseUrl(ServerHttpRequest request) {
        return isRunningLocally(request)
            ? parameterStoreAccessor.getShortUrlMappingServiceBaseUrlLocal()
            : parameterStoreAccessor.getShortUrlMappingServiceBaseUrlAws();
    }
}
