/*
 * Copyright 2018 Apple, Inc
 * Apple Internal Use Only
 */


package com.mts.service.impl;


import static com.mts.common.MetroTransisConstants.*;

import java.util.HashMap;
import java.util.Map;

import com.mts.controller.request.NextBusRequest;
import com.mts.domain.Direction;
import com.mts.domain.NexTripDeparture;
import com.mts.domain.Route;
import com.mts.domain.TextValuePair;
import com.mts.service.MetatDataService;
import com.mts.service.NextBusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;


@Service
public class NextBusServiceImpl implements NextBusService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MetatDataService metatDataService;

    @Value("${metro.baseUrl}")
    String baseUrl;

    @Value("${metro.nextTrip.url}")
    String nextTripUrl;

    /**
     * Get next trip details
     * 
     * @param nextBusRequest
     * @return
     */
    @Override
    public String getNextRouteBus(final NextBusRequest nextBusRequest) {
        Route route = metatDataService.getRoutes(nextBusRequest.getRoute());

        TextValuePair direction =
                    metatDataService.getRouteDirections(route.getRoute(), Direction.valueOf(nextBusRequest.getDirection().toUpperCase()).getText());
        TextValuePair stop = metatDataService.getRouteDirectionStops(route.getRoute(), direction.getValue(), nextBusRequest.getStop());

        UriTemplate template = new UriTemplate(baseUrl + nextTripUrl);
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(URI_ROUTE, route.getRoute());
        uriVariables.put(URI_DIRECTION, direction.getValue());
        uriVariables.put(URI_STOP, stop.getValue());

        ResponseEntity<NexTripDeparture[]> tripEntity = restTemplate.getForEntity(template.expand(uriVariables), NexTripDeparture[].class);
        NexTripDeparture[] trips = tripEntity.getBody();

        if (trips == null || trips.length == 0) {
            return "";
        }

        return trips[0].getDepartureText();
    }

}