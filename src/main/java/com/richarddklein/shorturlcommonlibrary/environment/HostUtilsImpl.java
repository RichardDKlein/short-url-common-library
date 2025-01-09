/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.environment;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

public class HostUtilsImpl implements HostUtils {
    private final ParameterStoreAccessor parameterStoreAccessor;

    @Value("${PROFILE}")
    private String profile;

    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------

    public HostUtilsImpl(ParameterStoreAccessor parameterStoreAccessor) {
        this.parameterStoreAccessor = parameterStoreAccessor;
    }

    @Override
    public Boolean isRunningLocally() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return isDockerContainer(hostName);
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
    public Mono<String> getShortUrlUserServiceBaseUrl() {
        if (isRunningLocally()) {
            return parameterStoreAccessor.getShortUrlUserServiceBaseUrlLocal();
        } else {
            return (profile.equals("prod"))
                ? parameterStoreAccessor.getShortUrlUserServiceBaseUrlAwsProd()
                : parameterStoreAccessor.getShortUrlUserServiceBaseUrlAwsTest();
        }
    }

    @Override
    public Mono<String> getShortUrlMappingServiceBaseUrl() {
        if (isRunningLocally()) {
            return parameterStoreAccessor.getShortUrlMappingServiceBaseUrlLocal();
        } else {
            return (profile.equals("prod"))
                ? parameterStoreAccessor.getShortUrlMappingServiceBaseUrlAwsProd()
                : parameterStoreAccessor.getShortUrlMappingServiceBaseUrlAwsTest();
        }
    }

    @Override
    public Mono<String> getShortUrlReservationServiceBaseUrl() {
        if (isRunningLocally()) {
            return parameterStoreAccessor.getShortUrlReservationServiceBaseUrlLocal();
        } else {
            return (profile.equals("prod"))
                ? parameterStoreAccessor.getShortUrlReservationServiceBaseUrlAwsProd()
                : parameterStoreAccessor.getShortUrlReservationServiceBaseUrlAwsTest();
        }
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static boolean isDockerContainer(String hostName) {
        return Pattern.matches("^[a-f0-9]{12}$", hostName);
    }
}
