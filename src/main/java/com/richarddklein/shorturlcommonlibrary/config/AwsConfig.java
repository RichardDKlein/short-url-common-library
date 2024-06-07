/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.config;

import com.richarddklein.shorturlcommonlibrary.aws.ParameterStoreReader;
import com.richarddklein.shorturlcommonlibrary.aws.ParameterStoreReaderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.ssm.SsmClient;

/**
 * The AWS @Configuration class.
 *
 * <p>Tells Spring how to construct instances of classes that are needed
 * to implement the AWS interface package.</p>
 */
@Configuration
public class AwsConfig {
    @Bean
    public SsmClient
    ssmClient() {
        return SsmClient.builder().build();
    }

    @Bean
    public ParameterStoreReader
    parameterStoreReader() {
        return new ParameterStoreReaderImpl(ssmClient());
    }
}
