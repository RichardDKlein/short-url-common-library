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
    private static final String ADMIN_USERNAME = "/shortUrl/users/adminUsername";
    private static final String ADMIN_PASSWORD = "/shortUrl/users/adminPassword";
    private static final String JWT_MINUTES_TO_LIVE = "/shortUrl/users/jwtMinutesToLive";
    private static final String JWT_SECRET_KEY = "/shortUrl/users/jwtSecretKey";
    private static final String SHORT_URL_MAPPING_TABLE_NAME =
            "/shortUrl/mappings/tableName";
    private static final String SHORT_URL_RANGE = "/shortUrl/reservations/range";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_AWS =
            "/shortUrl/reservations/baseUrlAws";
    private static final String SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL =
            "/shortUrl/reservations/baseUrlLocal";
    private static final String SHORT_URL_RESERVATION_TABLE_NAME = "/shortUrl/reservations/tableName";
    private static final String SHORT_URL_USER_TABLE_NAME = "/shortUrl/users/tableName";


    private final SsmClient ssmClient;

    private String adminPassword;
    private String adminUsername;
    private int jwtMinutesToLive;
    private String jwtSecretKey;
    private Long maxShortUrlBase10;
    private Long minShortUrlBase10;
    private String shortUrlMappingTableName;
    private String shortUrlReservationServiceBaseUrlAws;
    private String shortUrlReservationServiceBaseUrlLocal;
    private String shortUrlReservationTableName;
    private String shortUrlUserTableName;

    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------

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
    public String getAdminPassword() {
        if (adminPassword == null) {
            adminPassword = getParameter(ADMIN_PASSWORD);
        }
        return adminPassword;
    }

    @Override
    public String getAdminUsername() {
        if (adminUsername == null) {
            adminUsername = getParameter(ADMIN_USERNAME);
        }
        return adminUsername;
    }

    @Override
    public int getJwtMinutesToLive() {
        if (jwtMinutesToLive == 0) {
            jwtMinutesToLive = Integer.parseInt(getParameter(JWT_MINUTES_TO_LIVE));
        }
        return jwtMinutesToLive;
    }

    @Override
    public String getJwtSecretKey() {
        if (jwtSecretKey == null) {
            jwtSecretKey = getParameter(JWT_SECRET_KEY);
        }
        return jwtSecretKey;
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
    public String getShortUrlMappingTableName() {
        if (shortUrlMappingTableName == null) {
            shortUrlMappingTableName = getParameter(SHORT_URL_MAPPING_TABLE_NAME);
        }
        return shortUrlMappingTableName;
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
    public String getShortUrlReservationServiceBaseUrlLocal() {
        if (shortUrlReservationServiceBaseUrlLocal == null) {
            shortUrlReservationServiceBaseUrlLocal =
                    getParameter(SHORT_URL_RESERVATION_SERVICE_BASE_URL_LOCAL);
        }
        return shortUrlReservationServiceBaseUrlLocal;
    }

    @Override
    public String getShortUrlReservationTableName() {
        if (shortUrlReservationTableName == null) {
            shortUrlReservationTableName = getParameter(SHORT_URL_RESERVATION_TABLE_NAME);
        }
        return shortUrlReservationTableName;
    }

    @Override
    public String getShortUrlUserTableName() {
        if (shortUrlUserTableName == null) {
            shortUrlUserTableName = getParameter(SHORT_URL_USER_TABLE_NAME);
        }
        return shortUrlUserTableName;
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
