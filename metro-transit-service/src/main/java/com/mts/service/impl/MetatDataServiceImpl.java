

package com.mts.service.impl;


import static com.mts.common.MetroTransisConstants.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.mts.domain.Route;
import com.mts.domain.TextValuePair;
import com.mts.exception.BusinessException;
import com.mts.exception.BusinessExceptionMessage;
import com.mts.service.MetatDataService;
import com.mts.spring.MetroTransitServiceProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;



@Service
public class MetatDataServiceImpl implements MetatDataService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MetroTransitServiceProperties metroTransitServiceProperties;

    private Route[] routes;

    @PostConstruct
    public void initData() {
        ResponseEntity<Route[]> routesEntity =
                    restTemplate.getForEntity(metroTransitServiceProperties.getBaseUrl() + metroTransitServiceProperties.getRoutesUrl(), Route[].class);
        routes = routesEntity.getBody();
    }

    /**
     * Get route details from Cache
     * 
     * @param routeDesc e.g "METRO Blue Line"
     * @return
     */
    @Cacheable(cacheNames = "routes", unless = "#result == null")
    @Override
    public Route getRoutes(final String routeDesc) {
        Optional<Route> routeResult = Arrays.asList(routes).stream().filter(route -> route.getDescription().equals(routeDesc)).findFirst();

        if (routeResult.isEmpty()) {
            throw new BusinessException(new BusinessExceptionMessage(ERROR_ROUTE_CODE, ERROR_ROUTE_DESC));
        }
        return routeResult.get();
    }

    /**
     * Get direction details for the given route
     * 
     * @param routeId e.g "901"
     * @param directionDesc e.g "NORTHBOUND"
     * @return
     */
    @Cacheable(cacheNames = "routes", unless = "#result == null")
    @Override
    public TextValuePair getRouteDirections(final String routeId, final String directionDesc) {

        UriTemplate uriTemplate = new UriTemplate(metroTransitServiceProperties.getBaseUrl() + metroTransitServiceProperties.getDirectionsUrl());
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(URI_ROUTE, routeId);

        ResponseEntity<TextValuePair[]> directionsEntity = restTemplate.getForEntity(uriTemplate.expand(uriVariables), TextValuePair[].class);
        TextValuePair[] directions = directionsEntity.getBody();
        Optional<TextValuePair> dirsResult = Arrays.asList(directions).stream().filter(direction -> direction.getText().equals(directionDesc)).findFirst();

        if (dirsResult.isEmpty()) {
            throw new BusinessException(new BusinessExceptionMessage(ERROR_DIRECT_CODE, ERROR_DIRECT_DESC));
        }
        return dirsResult.get();
    }


    /**
     * Get Stop details for the given route, direction
     * 
     * @param routeId e.g "901"
     * @param directionId e.g "4"
     * @param stopDesc e.g "Mall of America Station"
     * @return
     */
    @Cacheable(cacheNames = "routes", unless = "#result == null")
    @Override
    public TextValuePair getRouteDirectionStops(final String routeId, final String directionId, final String stopDesc) {

        UriTemplate uriTemplate = new UriTemplate(metroTransitServiceProperties.getBaseUrl() + metroTransitServiceProperties.getStopsUrl());
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(URI_ROUTE, routeId);
        uriVariables.put(URI_DIRECTION, directionId);

        ResponseEntity<TextValuePair[]> stopsEntity = restTemplate.getForEntity(uriTemplate.expand(uriVariables), TextValuePair[].class);
        TextValuePair[] stops = stopsEntity.getBody();
        Optional<TextValuePair> stopsResult = Arrays.asList(stops).stream().filter(stop -> stop.getText().equals(stopDesc)).findFirst();

        if (stopsResult.isEmpty()) {
            throw new BusinessException(new BusinessExceptionMessage(ERROR_STOP_CODE, ERROR_STOP_DESC));
        }
        return stopsResult.get();
    }


}
