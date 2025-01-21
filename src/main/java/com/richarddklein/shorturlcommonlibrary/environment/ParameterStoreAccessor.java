/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.environment;

import reactor.core.publisher.Mono;

/**
 * The Parameter Store Accessor interface.
 *
 * <p>Specifies the methods that must be implemented by any class that
 * gets and sets certain parameters from the Parameter Store component
 * of the AWS Systems Manager.</p>
 */
public interface ParameterStoreAccessor {
    // Authentication
    // --------------
    Mono<String> getAdminPassword();
    Mono<Void> setAdminPassword(String adminPassword);
    Mono<String> getAdminUsername();
    Mono<Integer> getJwtMinutesToLive();
    Mono<Void> setJwtMinutesToLive(int jwtMinutesToLive);
    Mono<String> getJwtSecretKey();

    // Short Url Public API Service
    // ----------------------------
    Mono<String> getShortUrlPublicApiServiceBaseUrlAwsProd();
    Mono<String> getShortUrlPublicApiServiceBaseUrlAwsTest();
    Mono<String> getShortUrlPublicApiServiceBaseUrlLocal();

    // Short URL User Service
    // ----------------------
    Mono<String> getShortUrlUserServiceBaseUrlAwsProd();
    Mono<String> getShortUrlUserServiceBaseUrlAwsTest();
    Mono<String> getShortUrlUserServiceBaseUrlLocal();
    Mono<String> getShortUrlUserTableName();

    // Short URL Mapping Service
    // -------------------------
    Mono<String> getShortUrlMappingServiceBaseUrlAwsProd();
    Mono<String> getShortUrlMappingServiceBaseUrlAwsTest();
    Mono<String> getShortUrlMappingServiceBaseUrlLocal();
    Mono<String> getShortUrlMappingTableName();

    // Short URL Reservation Service
    // ----------------------------
    Mono<String> getShortUrlReservationServiceBaseUrlAwsProd();
    Mono<String> getShortUrlReservationServiceBaseUrlAwsTest();
    Mono<String> getShortUrlReservationServiceBaseUrlLocal();
    Mono<String> getShortUrlReservationTableName();
    Mono<Long> getMinShortUrlBase10();
    Mono<Long> getMaxShortUrlBase10();
}
