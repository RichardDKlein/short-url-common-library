/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.aws;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

/**
 * The production implementation of the Parameter Store Reader interface.
 */
@Component
public class ParameterStoreReaderImpl implements ParameterStoreReader {
    private static final String SHORT_URL_MAPPING_TABLE_NAME =
            "/shortUrl/mappings/tableName";
    private static final String SHORT_URL_RANGE = "/shortUrl/reservations/range";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS =
            "/shortUrl/reservations/baseUrlAws";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/reservations/baseUrlLocal";
    private static final String SHORT_URL_RESERVATION_TABLE_NAME = "/shortUrl/reservations/tableName";

    private final SsmClient ssmClient;

    private String shortUrlReservationTableName;
    private Long minShortUrlBase10;
    private Long maxShortUrlBase10;
    private String shortUrlReservationServiceBaseUrlLocal;
    private String shortUrlReservationServiceBaseUrlAws;

    private String shortUrlMappingTableName;

    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------

    @Override
    public String getShortUrlReservationTableName() {
        if (shortUrlReservationTableName == null) {
            shortUrlReservationTableName = getParameter(SHORT_URL_RESERVATION_TABLE_NAME);
        }
        return shortUrlReservationTableName;
    }

    @Override
    public long getMinShortUrlBase10() {
        if (minShortUrlBase10 == null) {
            String shortUrlRange = getParameter(SHORT_URL_RANGE);
            String[] tokens = shortUrlRange.split(",\\s*");
            minShortUrlBase10 = Long.parseLong(tokens[0]);
        }
        return minShortUrlBase10;
    }

    @Override
    public long getMaxShortUrlBase10() {
        if (maxShortUrlBase10 == null) {
            String shortUrlRange = getParameter(SHORT_URL_RANGE);
            String[] tokens = shortUrlRange.split(",\\s*");
            maxShortUrlBase10 = Long.parseLong(tokens[1]);
        }
        return maxShortUrlBase10;
    }

    /**
     * General constructor.
     *
     * @param ssmClient Dependency injection of a class instance that is to play
     *                  the role of an SSM (Simple Systems Manager) Client.
     */
    public ParameterStoreReaderImpl(SsmClient ssmClient) {
        this.ssmClient = ssmClient;
    }

    @Override
    public String getShortUrlReservationServiceBaseUrlLocal() {
        if (shortUrlReservationServiceBaseUrlLocal == null) {
            shortUrlReservationServiceBaseUrlLocal =
                    getParameter(SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL);
        }
        return shortUrlReservationServiceBaseUrlLocal;
    }

    @Override
    public String getShortUrlReservationServiceBaseUrlAws() {
        if (shortUrlReservationServiceBaseUrlAws == null) {
            shortUrlReservationServiceBaseUrlAws =
                    getParameter(SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS);
        }
        return shortUrlReservationServiceBaseUrlAws;
    }

    @Override
    public String getShortUrlMappingTableName() {
        if (shortUrlMappingTableName == null) {
            shortUrlMappingTableName = getParameter(SHORT_URL_MAPPING_TABLE_NAME);
        }
        return shortUrlMappingTableName;
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    /**
     * Get a parameter from the Parameter Store.
     *
     * @param parameterName The name of the parameter of interest.
     * @return The value of the parameter in the Parameter Store.
     */
    private String getParameter(String parameterName) {
        GetParameterResponse parameterResponse =
                ssmClient.getParameter(req -> req.name(parameterName));
        return parameterResponse.parameter().value();
    }
}
