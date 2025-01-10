/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.security;

import com.richarddklein.shorturlcommonlibrary.environment.ParameterStoreAccessor;
import com.richarddklein.shorturlcommonlibrary.security.adminbasicauthentication.*;
import com.richarddklein.shorturlcommonlibrary.security.adminjwttokenauthentication.*;
import com.richarddklein.shorturlcommonlibrary.security.util.JwtUtils;
import com.richarddklein.shorturlcommonlibrary.security.util.JwtUtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/**
 * The Security @Configuration class.
 *
 * <p>Tells Spring how to construct instances of classes that are needed
 * to implement security (using Spring Security).</p>
 */
@Order(1)
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired
    ParameterStoreAccessor parameterStoreAccessor;

    @Bean
    public SecurityWebFilterChain
    securityWebFilterChain(ServerHttpSecurity http) {
        return http
                // Disable default security.
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)

                // Authorize all endpoints unconditionally.
                .authorizeExchange(authorize -> authorize.anyExchange().permitAll())

                .build();
    }

    // ------------------------------------------------------------------------
    // ADMIN BASIC AUTHENTICATION WEB FILTER
    // ------------------------------------------------------------------------

    @Bean
    public AdminBasicAuthenticationWebFilter
    adminBasicAuthenticationWebFilter() {
        return new AdminBasicAuthenticationWebFilterImpl(
                adminBasicAuthenticationManager(),
                adminBasicAuthenticationConverter(),
                adminAuthenticationFailureHandler());
    }

    @Bean
    @Primary
    public AdminBasicAuthenticationManager
    adminBasicAuthenticationManager() {
        return new AdminBasicAuthenticationManagerImpl();
    }

    @Bean
    public AdminBasicAuthenticationConverter
    adminBasicAuthenticationConverter() {
        return new AdminBasicAuthenticationConverterImpl();
    }

    @Bean
    public AdminBasicAuthenticationFailureHandler
    adminAuthenticationFailureHandler() {
        return new AdminBasicAuthenticationFailureHandlerImpl();
    }

    // ------------------------------------------------------------------------
    // ADMIN JWT TOKEN AUTHENTICATION WEB FILTER
    // ------------------------------------------------------------------------

    @Bean
    public AdminJwtTokenAuthenticationWebFilter
    adminJwtTokenAuthenticationWebFilter() {
        return new AdminJwtTokenAuthenticationWebFilterImpl(
                adminJwtTokenAuthenticationManager(),
                jwtTokenAuthenticationConverter(),
                jwtTokenAuthenticationFailureHandler());
    }

    @Bean
    public AdminJwtTokenAuthenticationManager
    adminJwtTokenAuthenticationManager() {
        return new AdminJwtTokenAuthenticationManagerImpl();
    }

    @Bean
    public AdminJwtTokenAuthenticationConverter
    jwtTokenAuthenticationConverter() {
        return new AdminJwtTokenAuthenticationConverterImpl();
    }

    @Bean
    public AdminJwtTokenAuthenticationFailureHandler
    jwtTokenAuthenticationFailureHandler() {
        return new AdminJwtTokenAuthenticationFailureHandlerImpl();
    }

    // ------------------------------------------------------------------------
    // SECURITY UTILITIES
    // ------------------------------------------------------------------------

    @Bean
    public PasswordEncoder
    passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtils
    jwtUtils() {
        return new JwtUtilsImpl(parameterStoreAccessor);
    }
}
