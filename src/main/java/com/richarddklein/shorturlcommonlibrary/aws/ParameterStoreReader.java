/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.aws;

/**
 * The Parameter Store Reader interface.
 *
 * <p>Specifies the methods that must be implemented by any class that
 * reads certain parameters from the Parameter Store component of the
 * AWS Systems Manager.</p>
 */
public interface ParameterStoreReader {
    /**
     * Get the name of the Short URL Reservation table in the DynamoDB
     * database.
     *
     * @return The name of the Short URL Reservation table in the DynamoDB
     * database.
     */
    String getShortUrlReservationTableName();

    /**
     * Get the minimum short URL, in base-10 representation.
     *
     * @return The base-10 representation of the minimum value of the
     * range of short URLs that are to be stored in the Short URL Reservation
     * table in the DynamoDB database.
     */
    long getMinShortUrlBase10();

    /**
     * Get the maximum short URL, in base-10 representation.
     *
     * @return The base-10 representation of the maximum value of the
     * range of short URLs that are to be stored in the Short URL Reservation
     * table in the DynamoDB database.
     */
    long getMaxShortUrlBase10();

    /**
     * Get the base URL of the Short URL Reservation Service when
     * that service is running on your local machine.
     *
     * @return The base URL.
     */
    String getShortUrlReservationServiceBaseUrlLocal();

    /**
     * Get the base URL of the Short URL Reservation Service when
     * that service is running on AWS.
     *
     * @return The base URL.
     */
    String getShortUrlReservationServiceBaseUrlAws();

    /**
     * Get the name of the Short URL Mapping table in the DynamoDB
     * database.
     *
     * @return The name of the Short URL Mapping table in the DynamoDB
     * database.
     */
    String getShortUrlMappingTableName();
}
