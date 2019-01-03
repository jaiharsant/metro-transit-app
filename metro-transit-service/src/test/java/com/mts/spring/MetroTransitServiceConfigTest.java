/*
 * Copyright 2018 abc, Inc
 * abc Internal Use Only
 */


package com.mts.spring;


import java.util.Arrays;

import com.mts.controller.NextBusServiceController;
import com.mts.service.MetatDataService;
import com.mts.service.NextBusService;
import com.mts.service.impl.MetatDataServiceImpl;
import com.mts.service.impl.NextBusServiceImpl;
import com.mts.validation.RequestValidator;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@TestConfiguration
@EnableConfigurationProperties
@EnableCaching
@Import({ HttpClientConfig.class, MetroTransitServiceProperties.class })
@PropertySource("classpath:application_test.properties")
public class MetroTransitServiceConfigTest {

    @Autowired
    CloseableHttpClient httpClient;

    @Bean
    public RequestValidator getRequestValidator() {
        return new RequestValidator();
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                    new ConcurrentMapCache("routes")));
        return cacheManager;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }

    @Bean
    NextBusServiceController getNextBusServiceController() {
        return new NextBusServiceController();
    }

    @Bean
    NextBusService getNextBusService() {
        return new NextBusServiceImpl();
    }

    @Bean
    MetatDataService getMetatDataService() {
        return new MetatDataServiceImpl();
    }
}
