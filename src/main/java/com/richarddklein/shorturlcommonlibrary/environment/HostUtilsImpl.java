/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.environment;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public class HostUtilsImpl implements HostUtils {
    private final ParameterStoreAccessor parameterStoreAccessor;

    @Value("${PROFILE}")
    private String profile;

    public HostUtilsImpl(ParameterStoreAccessor parameterStoreAccessor) {
        this.parameterStoreAccessor = parameterStoreAccessor;
    }

    @Override
    public Boolean isRunningLocally(ServerHttpRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        return request.getURI().getHost().equals("localhost");
    }

    public Mono<String> getDomain() {
        return parameterStoreAccessor.getShortUrlUserServiceBaseUrlAwsProd()
            .flatMap(url -> {
                String domain = "";
                try {
                    URI uri = new URI(url);
                    String host = uri.getHost();
                    String[] parts = host.split("\\.");
                    domain = parts[parts.length - 2] + "." + parts[parts.length - 1];
                } catch (URISyntaxException e) {
                    return Mono.error(new RuntimeException("Invalid URL: " + url, e));
                }
                return Mono.just(domain);
        });
    }


    @Override
    public Mono<String> getShortUrlUserServiceBaseUrl(ServerHttpRequest request) {
        if (isRunningLocally(request)) {
            return parameterStoreAccessor.getShortUrlUserServiceBaseUrlLocal();
        } else {
            return (profile.equals("prod"))
                ? parameterStoreAccessor.getShortUrlUserServiceBaseUrlAwsProd()
                : parameterStoreAccessor.getShortUrlUserServiceBaseUrlAwsTest();
        }
    }

    @Override
    public Mono<String> getShortUrlMappingServiceBaseUrl(ServerHttpRequest request) {
        if (isRunningLocally(request)) {
            return parameterStoreAccessor.getShortUrlMappingServiceBaseUrlLocal();
        } else {
            return (profile.equals("prod"))
                ? parameterStoreAccessor.getShortUrlMappingServiceBaseUrlAwsProd()
                : parameterStoreAccessor.getShortUrlMappingServiceBaseUrlAwsTest();
        }
    }

    @Override
    public Mono<String> getShortUrlReservationServiceBaseUrl(ServerHttpRequest request) {
        if (isRunningLocally(request)) {
            return parameterStoreAccessor.getShortUrlReservationServiceBaseUrlLocal();
        } else {
            return (profile.equals("prod"))
                ? parameterStoreAccessor.getShortUrlReservationServiceBaseUrlAwsProd()
                : parameterStoreAccessor.getShortUrlReservationServiceBaseUrlAwsTest();
        }
    }
}
