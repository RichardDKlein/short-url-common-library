/**
 * The Short URL WebClient Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlwebclientlibrary.aws;

/**
 * The Parameter Store Reader interface.
 *
 * <p>Specifies the methods that must be implemented by any class that
 * reads certain parameters from the Parameter Store component of the
 * AWS Systems Manager.</p>
 */
public interface ParameterStoreReader {
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
}
