/**
 * The Short URL WebClient Library
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
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/reservations/baseUrlLocal";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS =
            "/shortUrl/reservations/baseUrlAws";

    private final SsmClient ssmClient = SsmClient.builder().build();

    private String shortUrlReservationServiceBaseUrlLocal;
    private String shortUrlReservationServiceBaseUrlAws;

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
