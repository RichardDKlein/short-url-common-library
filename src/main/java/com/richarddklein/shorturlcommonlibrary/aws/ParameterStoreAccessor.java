/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.aws;

import reactor.core.publisher.Mono;

/**
 * The Parameter Store Accessor interface.
 *
 * <p>Specifies the methods that must be implemented by any class that
 * gets and sets certain parameters from the Parameter Store component
 * of the AWS Systems Manager.</p>
 */
public interface ParameterStoreAccessor {
    /**
     * Get the name of the Short URL Reservation table in the DynamoDB
     * database.
     *
     * @return The name of the Short URL Reservation table in the DynamoDB
     * database.
     */
    Mono<String> getShortUrlReservationTableName();

    /**
     * Get the minimum short URL, in base-10 representation.
     *
     * @return The base-10 representation of the minimum value of the
     * range of short URLs that are to be stored in the Short URL Reservation
     * table in the DynamoDB database.
     */
    Mono<Long> getMinShortUrlBase10();

    /**
     * Get the maximum short URL, in base-10 representation.
     *
     * @return The base-10 representation of the maximum value of the
     * range of short URLs that are to be stored in the Short URL Reservation
     * table in the DynamoDB database.
     */
    Mono<Long> getMaxShortUrlBase10();

    /**
     * Get the base URL of the Short URL Reservation Service when
     * that service is running on your local machine.
     *
     * @return The base URL.
     */
    Mono<String> getShortUrlReservationServiceBaseUrlLocal();

    /**
     * Get the base URL of the Short URL Reservation Service when
     * that service is running on AWS.
     *
     * @return The base URL.
     */
    Mono<String> getShortUrlReservationServiceBaseUrlAws();

    /**
     * Get the name of the Short URL Mapping table in the DynamoDB
     * database.
     *
     * @return The name of the Short URL Mapping table in the DynamoDB
     * database.
     */
    Mono<String> getShortUrlMappingTableName();

    /**
     * Get the admin (unencrypted) username
     *
     * @return The admin (unencrypted) username
     */
    Mono<String> getAdminUsername();

    /**
     * Get the admin (unencrypted) password
     *
     * @return The admin (unencrypted) password
     */
    Mono<String> getAdminPassword();

    /**
     * Set the admin (unencrypted) password
     */
    Mono<Void> setAdminPassword(String adminPassword);

    /**
     * Get the number of minutes that a JWT token should live
     * before it expires.
     *
     * @return The JWT token minutes to live.
     */
    Mono<Integer> getJwtMinutesToLive();

    /**
     * Get the secret key used to encode/decode JWT tokens.
     *
     * @return The JWT token secret key.
     */
    Mono<String> getJwtSecretKey();

    /**
     * Get the name of the Short URL User table in the DynamoDB
     * database.
     *
     * @return The name of the Short URL User table in the DynamoDB
     * database.
     */
    Mono<String> getShortUrlUserTableName();

    Mono<String> getShortUrlUserServiceBaseUrlAws();
    Mono<String> getShortUrlUserServiceBaseUrlLocal();
    Mono<String> getShortUrlMappingServiceBaseUrlAws();
    Mono<String> getShortUrlMappingServiceBaseUrlLocal();
    Mono<String> getShortUrlPublicApiServiceBaseUrlAws();
    Mono<String> getShortUrlPublicApiServiceBaseUrlLocal();
}
