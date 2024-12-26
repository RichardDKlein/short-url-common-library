/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.config;

import com.richarddklein.shorturlcommonlibrary.aws.ParameterStoreAccessor;
import com.richarddklein.shorturlcommonlibrary.security.adminbasicauthentication.*;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.common.*;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleadmin.AdminJwtTokenAuthenticationManager;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleadmin.AdminJwtTokenAuthenticationManagerImpl;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleadmin.AdminJwtTokenAuthenticationWebFilter;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleadmin.AdminJwtTokenAuthenticationWebFilterImpl;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleuser.UserJwtTokenAuthenticationManager;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleuser.UserJwtTokenAuthenticationManagerImpl;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleuser.UserJwtTokenAuthenticationWebFilter;
import com.richarddklein.shorturlcommonlibrary.security.jwttokenauthentication.roleuser.UserJwtTokenAuthenticationWebFilterImpl;
import com.richarddklein.shorturlcommonlibrary.security.util.HostUtils;
import com.richarddklein.shorturlcommonlibrary.security.util.HostUtilsImpl;
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
    // ADMIN AND USER JWT TOKEN AUTHENTICATION WEB FILTERS
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
    public UserJwtTokenAuthenticationWebFilter
    userJwtTokenAuthenticationWebFilter() {
        return new UserJwtTokenAuthenticationWebFilterImpl(
                userJwtTokenAuthenticationManager(),
                jwtTokenAuthenticationConverter(),
                jwtTokenAuthenticationFailureHandler());
    }

    @Bean
    public AdminJwtTokenAuthenticationManager
    adminJwtTokenAuthenticationManager() {
        return new AdminJwtTokenAuthenticationManagerImpl();
    }

    @Bean
    public UserJwtTokenAuthenticationManager
    userJwtTokenAuthenticationManager() {
        return new UserJwtTokenAuthenticationManagerImpl();
    }

    @Bean
    public JwtTokenAuthenticationConverter
    jwtTokenAuthenticationConverter() {
        return new JwtTokenAuthenticationConverterImpl();
    }

    @Bean
    public JwtTokenAuthenticationFailureHandler
    jwtTokenAuthenticationFailureHandler() {
        return new JwtTokenAuthenticationFailureHandlerImpl();
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
    public HostUtils
    hostUtils() {
        return new HostUtilsImpl();
    }

    @Bean
    public JwtUtils
    jwtUtils() {
        return new JwtUtilsImpl(parameterStoreAccessor);
    }
}
