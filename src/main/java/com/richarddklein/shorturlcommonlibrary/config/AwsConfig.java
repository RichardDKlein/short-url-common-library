/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.config;

import com.richarddklein.shorturlcommonlibrary.environment.ParameterStoreAccessor;
import com.richarddklein.shorturlcommonlibrary.environment.ParameterStoreAccessorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.ssm.SsmAsyncClient;

/**
 * The AWS @Configuration class.
 *
 * <p>Tells Spring how to construct instances of classes that are needed
 * to implement the AWS interface package.</p>
 */
@Configuration
public class AwsConfig {
    @Bean
    public SsmAsyncClient
    ssmAsyncClient() {
        return SsmAsyncClient.builder().build();
    }

    @Bean
    public ParameterStoreAccessor
    parameterStoreReader() {
        return new ParameterStoreAccessorImpl(ssmAsyncClient());
    }
}
