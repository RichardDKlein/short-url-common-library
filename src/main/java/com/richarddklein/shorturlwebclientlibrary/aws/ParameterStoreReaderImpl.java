/**
 * The Short URL Mapping Service
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlwebclientlibrary.aws;

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
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/reservations/baseUrlLocal";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS =
            "/shortUrl/reservations/baseUrlAws";

    private final SsmClient ssmClient;

    private String shortUrlMappingTableName;
    private String shortUrlReservationServiceBaseUrlLocal;
    private String shortUrlReservationServiceBaseUrlAws;

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
    public String getShortUrlMappingTableName() {
        if (shortUrlMappingTableName == null) {
            shortUrlMappingTableName = getParameter(SHORT_URL_MAPPING_TABLE_NAME);
        }
        return shortUrlMappingTableName;
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