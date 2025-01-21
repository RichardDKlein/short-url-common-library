/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.environment;

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
    private static final String JWT_MINUTES_TO_LIVE_PROD =
            "/shortUrl/users/jwtMinutesToLiveProd";
    private static final String JWT_MINUTES_TO_LIVE_TEST =
            "/shortUrl/users/jwtMinutesToLiveTest";
    private static final String JWT_SECRET_KEY =
            "/shortUrl/users/jwtSecretKey";
    private static final String SHORT_URL_MAPPING_SERVICE_BASE_URL_AWS_PROD =
            "/shortUrl/mappings/baseUrlAwsProd";
    private static final String SHORT_URL_MAPPING_SERVICE_BASE_URL_AWS_TEST =
            "/shortUrl/mappings/baseUrlAwsTest";
    private static final String SHORT_URL_MAPPING_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/mappings/baseUrlLocal";
    private static final String SHORT_URL_MAPPING_TABLE_NAME =
            "/shortUrl/mappings/tableName";
    private static final String SHORT_URL_PUBLIC_API_SERVICE_BASE_URL_AWS_PROD =
            "/shortUrl/publicApi/baseUrlAwsProd";
    private static final String SHORT_URL_PUBLIC_API_SERVICE_BASE_URL_AWS_TEST =
            "/shortUrl/publicApi/baseUrlAwsTest";
    private static final String SHORT_URL_PUBLIC_API_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/publicApi/baseUrlLocal";
    private static final String SHORT_URL_RANGE =
            "/shortUrl/reservations/range";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS_PROD =
            "/shortUrl/reservations/baseUrlAwsProd";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS_TEST =
            "/shortUrl/reservations/baseUrlAwsTest";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/reservations/baseUrlLocal";
    private static final String SHORT_URL_RESERVATION_TABLE_NAME =
            "/shortUrl/reservations/tableName";
    private static final String SHORT_URL_USER_SERVICE_BASE_URL_AWS_PROD =
            "/shortUrl/users/baseUrlAwsProd";
    private static final String SHORT_URL_USER_SERVICE_BASE_URL_AWS_TEST =
            "/shortUrl/users/baseUrlAwsTest";
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
        return getParameter(ADMIN_PASSWORD, false);
    }

    @Override
    public Mono<Void> setAdminPassword(String adminPassword) {
        return setParameter(ADMIN_PASSWORD, adminPassword, false);
    }

    @Override
    public Mono<String> getAdminUsername() {
        return getParameter(ADMIN_USERNAME, false);
    }

    @Override
    public Mono<Integer> getJwtMinutesToLive() {
        if (profile.equals("prod")) {
            return getParameter(JWT_MINUTES_TO_LIVE_PROD, false).map(Integer::parseInt);
        } else if (profile.equals("test")) {
            return getParameter(JWT_MINUTES_TO_LIVE_TEST, true).map(Integer::parseInt);
        } else {
            return Mono.error(new RuntimeException("Invalid profile: " + profile));
        }
    }

    @Override
    public Mono<Void> setJwtMinutesToLive(int jwtMinutesToLive) {
        if (profile.equals("prod")) {
            return setParameter(JWT_MINUTES_TO_LIVE_PROD, Integer.toString(jwtMinutesToLive), false);
        } else if (profile.equals("test")) {
            return setParameter(JWT_MINUTES_TO_LIVE_TEST, Integer.toString(jwtMinutesToLive), true);
        } else {
            return Mono.error(new RuntimeException("Invalid profile: " + profile));
        }
    }

    @Override
    public Mono<String> getJwtSecretKey() {
        return getParameter(JWT_SECRET_KEY, false);
    }

    // ------------------------------------------------------------------------
    // SHORT URL PUBLIC API SERVICE
    // ------------------------------------------------------------------------

    @Override
    public Mono<String> getShortUrlPublicApiServiceBaseUrlAwsProd() {
        return getParameter(SHORT_URL_PUBLIC_API_SERVICE_BASE_URL_AWS_PROD, false);
    }

    @Override
    public Mono<String> getShortUrlPublicApiServiceBaseUrlAwsTest() {
        return getParameter(SHORT_URL_PUBLIC_API_SERVICE_BASE_URL_AWS_TEST, false);
    }

    @Override
    public Mono<String> getShortUrlPublicApiServiceBaseUrlLocal() {
        return getParameter(SHORT_URL_PUBLIC_API_SERVICE_BASE_URL_LOCAL, false);
    }

    // ------------------------------------------------------------------------
    // SHORT URL USER SERVICE
    // ------------------------------------------------------------------------

    @Override
    public Mono<String> getShortUrlUserServiceBaseUrlAwsProd() {
        return getParameter(SHORT_URL_USER_SERVICE_BASE_URL_AWS_PROD, false);
    }

    @Override
    public Mono<String> getShortUrlUserServiceBaseUrlAwsTest() {
        return getParameter(SHORT_URL_USER_SERVICE_BASE_URL_AWS_TEST, false);
    }

    @Override
    public Mono<String> getShortUrlUserServiceBaseUrlLocal() {
        return getParameter(SHORT_URL_USER_SERVICE_BASE_URL_LOCAL, false);
    }

    @Override
    public Mono<String> getShortUrlUserTableName() {
        return getParameter(SHORT_URL_USER_TABLE_NAME, false).map(tableName -> {
            System.out.printf("====> profile = %s\n", profile);
            if (profile.equals("test")) {
                tableName = "test-" + tableName;
            }
            System.out.printf("====> shortUrlUserTableName = %s\n", tableName);
            return tableName;
        });
    }

    // ------------------------------------------------------------------------
    // SHORT URL MAPPING SERVICE
    // ------------------------------------------------------------------------

    @Override
    public Mono<String> getShortUrlMappingServiceBaseUrlAwsProd() {
        return getParameter(SHORT_URL_MAPPING_SERVICE_BASE_URL_AWS_PROD, false);
    }

    @Override
    public Mono<String> getShortUrlMappingServiceBaseUrlAwsTest() {
        return getParameter(SHORT_URL_MAPPING_SERVICE_BASE_URL_AWS_TEST, false);
    }

    @Override
    public Mono<String> getShortUrlMappingServiceBaseUrlLocal() {
        return getParameter(SHORT_URL_MAPPING_SERVICE_BASE_URL_LOCAL, false);
    }

    @Override
    public Mono<String> getShortUrlMappingTableName() {
        return getParameter(SHORT_URL_MAPPING_TABLE_NAME, false).map(tableName -> {
            System.out.printf("====> profile = %s\n", profile);
            if (profile.equals("test")) {
                tableName = "test-" + tableName;
            }
            System.out.printf("====> shortUrlMappingTableName = %s\n", tableName);
            return tableName;
        });
    }

    // ------------------------------------------------------------------------
    // SHORT URL RESERVATION SERVICE
    // ------------------------------------------------------------------------

    @Override
    public Mono<String> getShortUrlReservationServiceBaseUrlAwsProd() {
        return getParameter(SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS_PROD, false);
    }

    @Override
    public Mono<String> getShortUrlReservationServiceBaseUrlAwsTest() {
        return getParameter(SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS_TEST, false);
    }

    @Override
    public Mono<String> getShortUrlReservationServiceBaseUrlLocal() {
        return getParameter(SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL, false);
    }

    @Override
    public Mono<String> getShortUrlReservationTableName() {
        return getParameter(SHORT_URL_RESERVATION_TABLE_NAME, false).map(tableName -> {
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
        return getParameter(SHORT_URL_RANGE, false).map(range -> {
            String[] tokens = range.split(",\\s*");
            return Long.parseLong(tokens[0]);
        });
    }
    @Override
    public Mono<Long> getMaxShortUrlBase10() {
        return getParameter(SHORT_URL_RANGE, false).map(range -> {
            String[] tokens = range.split(",\\s*");
            return Long.parseLong(tokens[1]);
        });
    }

    // ========================================================================
    // PRIVATE METHODS
    // ========================================================================

    private Mono<String> getParameter(String parameterName, boolean bypassCache) {
        if (bypassCache) {
            // Fetch directly from Parameter Store, skipping the cache
            return Mono.fromFuture(ssmAsyncClient.getParameter(req -> req.name(parameterName)))
                .map(getParameterResponse -> getParameterResponse.parameter().value());
        } else {
            // Use cached value if available
            return cache.computeIfAbsent(parameterName, key ->
                Mono.fromFuture(ssmAsyncClient.getParameter(req -> req.name(key)))
                    .map(getParameterResponse -> getParameterResponse.parameter().value())
                    .cache()
            );
        }
    }

    private Mono<Void> setParameter(String parameterName, String parameterValue, boolean bypassCache) {
        return Mono.fromFuture(ssmAsyncClient.putParameter(req -> req
                .name(parameterName)
                .value(parameterValue)
                .type(ParameterType.STRING)
                .overwrite(true)))
            .then()
            .doOnSuccess(v -> {
                if (!bypassCache) {
                    // Update the cache if caching is not bypassed
                    cache.put(parameterName, Mono.just(parameterValue));
                }
            });
    }
}
