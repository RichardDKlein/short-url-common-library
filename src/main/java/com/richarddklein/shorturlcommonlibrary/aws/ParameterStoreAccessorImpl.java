/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.aws;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.ssm.SsmAsyncClient;
import software.amazon.awssdk.services.ssm.model.ParameterType;

/**
 * The production implementation of the Parameter Store Reader interface.
 */
@Component
public class ParameterStoreAccessorImpl implements ParameterStoreAccessor {
    private static final String ADMIN_USERNAME =
            "/shortUrl/users/adminUsername";
    private static final String ADMIN_PASSWORD =
            "/shortUrl/users/adminPassword";
    private static final String JWT_MINUTES_TO_LIVE =
            "/shortUrl/users/jwtMinutesToLive";
    private static final String JWT_SECRET_KEY =
            "/shortUrl/users/jwtSecretKey";
    private static final String SHORT_URL_MAPPING_SERVICE_BASE_URL_AWS =
            "/shortUrl/mappings/baseUrlAws";
    private static final String SHORT_URL_MAPPING_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/mappings/baseUrlLocal";
    private static final String SHORT_URL_MAPPING_TABLE_NAME =
            "/shortUrl/mappings/tableName";
    private static final String SHORT_URL_RANGE =
            "/shortUrl/reservations/range";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS =
            "/shortUrl/reservations/baseUrlAws";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/reservations/baseUrlLocal";
    private static final String SHORT_URL_RESERVATION_TABLE_NAME =
            "/shortUrl/reservations/tableName";
    private static final String SHORT_URL_USER_SERVICE_BASE_URL_AWS =
            "/shortUrl/users/baseUrlAws";
    private static final String SHORT_URL_USER_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/users/baseUrlLocal";
    private static final String SHORT_URL_USER_TABLE_NAME =
            "/shortUrl/users/tableName";

    private final ConcurrentMap<String, Mono<String>> cache =
            new ConcurrentHashMap<>();

    private final SsmAsyncClient ssmAsyncClient;

    @Value("${PROFILE}")
    private String profile;

    // ========================================================================
    // PUBLIC METHODS
    // ========================================================================

    public ParameterStoreAccessorImpl(SsmAsyncClient ssmAsyncClient) {
        this.ssmAsyncClient = ssmAsyncClient;
    }

    // ------------------------------------------------------------------------
    // AUTHENTICATION
    // ------------------------------------------------------------------------

    @Override
    public Mono<String> getAdminPassword() {
        return getParameter(ADMIN_PASSWORD);
    }

    @Override
    public Mono<Void> setAdminPassword(String adminPassword) {
        return setParameter(ADMIN_PASSWORD, adminPassword);
    }

    @Override
    public Mono<String> getAdminUsername() {
        return getParameter(ADMIN_USERNAME);
    }

    @Override
    public Mono<Integer> getJwtMinutesToLive() {
        return getParameter(JWT_MINUTES_TO_LIVE).map(Integer::parseInt);
    }

    @Override
    public Mono<String> getJwtSecretKey() {
        return getParameter(JWT_SECRET_KEY);
    }

    // ------------------------------------------------------------------------
    // SHORT URL USERS
    // ------------------------------------------------------------------------

    @Override
    public Mono<String> getShortUrlUserServiceBaseUrlAws() {
        return getParameter(SHORT_URL_USER_SERVICE_BASE_URL_AWS);
    }

    @Override
    public Mono<String> getShortUrlUserServiceBaseUrlLocal() {
        return getParameter(SHORT_URL_USER_SERVICE_BASE_URL_LOCAL);
    }

    @Override
    public Mono<String> getShortUrlUserTableName() {
        return getParameter(SHORT_URL_USER_TABLE_NAME).map(tableName -> {
            System.out.printf("====> profile = %s\n", profile);
            if (profile.equals("test")) {
                tableName = "test-" + tableName;
            }
            System.out.printf("====> shortUrlUserTableName = %s\n", tableName);
            return tableName;
        });
    }

    // ------------------------------------------------------------------------
    // SHORT URL RESERVATIONS
    // ------------------------------------------------------------------------

    @Override
    public Mono<String> getShortUrlReservationServiceBaseUrlAws() {
        return getParameter(SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS);
    }

    @Override
    public Mono<String> getShortUrlReservationServiceBaseUrlLocal() {
        return getParameter(SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL);
    }

    @Override
    public Mono<String> getShortUrlReservationTableName() {
        return getParameter(SHORT_URL_RESERVATION_TABLE_NAME).map(tableName -> {
            System.out.printf("====> profile = %s\n", profile);
            if (profile.equals("test")) {
                tableName = "test-" + tableName;
            }
            System.out.printf("====> shortUrlReservationTableName = %s\n", tableName);
            return tableName;
        });
    }

    @Override
    public Mono<Long> getMinShortUrlBase10() {
        return getParameter(SHORT_URL_RANGE).map(range -> {
            String[] tokens = range.split(",\\s*");
            return Long.parseLong(tokens[0]);
        });
    }
    @Override
    public Mono<Long> getMaxShortUrlBase10() {
        return getParameter(SHORT_URL_RANGE).map(range -> {
            String[] tokens = range.split(",\\s*");
            return Long.parseLong(tokens[1]);
        });
    }

    // ------------------------------------------------------------------------
    // SHORT URL MAPPINGS
    // ------------------------------------------------------------------------

    @Override
    public Mono<String> getShortUrlMappingServiceBaseUrlAws() {
        return getParameter(SHORT_URL_MAPPING_SERVICE_BASE_URL_AWS);
    }

    @Override
    public Mono<String> getShortUrlMappingServiceBaseUrlLocal() {
        return getParameter(SHORT_URL_MAPPING_SERVICE_BASE_URL_LOCAL);
    }

    @Override
    public Mono<String> getShortUrlMappingTableName() {
        return getParameter(SHORT_URL_MAPPING_TABLE_NAME).map(tableName -> {
            System.out.printf("====> profile = %s\n", profile);
            if (profile.equals("test")) {
                tableName = "test-" + tableName;
            }
            System.out.printf("====> shortUrlMappingTableName = %s\n", tableName);
            return tableName;
        });
    }

    // ========================================================================
    // PRIVATE METHODS
    // ========================================================================

    private Mono<String> getParameter(String parameterName) {
        return cache.computeIfAbsent(parameterName, key ->
            Mono.fromFuture(ssmAsyncClient.getParameter(req -> req.name(key)))
                .map(getParameterResponse -> getParameterResponse.parameter().value())
                .cache()
        );
    }

    private Mono<Void> setParameter(String parameterName, String parameterValue) {
        return Mono.fromFuture(ssmAsyncClient.putParameter(req -> req
            .name(parameterName)
            .value(parameterValue)
            .type(ParameterType.STRING)
            .overwrite(true)))
            .then()
            .doOnSuccess(v -> cache.put(parameterName, Mono.just(parameterValue)));
    }
}
