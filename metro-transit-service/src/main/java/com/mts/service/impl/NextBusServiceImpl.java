

package com.mts.service.impl;


import static com.mts.common.MetroTransisConstants.*;

import java.util.HashMap;
import java.util.Map;

import com.mts.controller.request.NextBusRequest;
import com.mts.controller.response.NextBusResponse;
import com.mts.domain.Direction;
import com.mts.domain.NexTripDeparture;
import com.mts.domain.Route;
import com.mts.domain.TextValuePair;
import com.mts.exception.BusinessException;
import com.mts.exception.BusinessExceptionMessage;
import com.mts.service.MetatDataService;
import com.mts.service.NextBusService;
import com.mts.spring.MetroTransitServiceProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;


@Service
public class NextBusServiceImpl implements NextBusService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MetatDataService metatDataService;

    @Autowired
    private MetroTransitServiceProperties metroTransitServiceProperties;

    /**
     * Get next trip details
     * 
     * @param nextBusRequest
     * @return
     */
    @Override
    public NextBusResponse getNextRouteBus(final NextBusRequest nextBusRequest) {
        Route route = metatDataService.getRoutes(nextBusRequest.getRoute());

        TextValuePair direction =
                    metatDataService.getRouteDirections(route.getRoute(), Direction.valueOf(nextBusRequest.getDirection().toUpperCase()).getText());

        TextValuePair stop = metatDataService.getRouteDirectionStops(route.getRoute(), direction.getValue(), nextBusRequest.getStop());

        UriTemplate template = new UriTemplate(metroTransitServiceProperties.getBaseUrl() + metroTransitServiceProperties.getNextTripUrl());
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(URI_ROUTE, route.getRoute());
        uriVariables.put(URI_DIRECTION, direction.getValue());
        uriVariables.put(URI_STOP, stop.getValue());

        ResponseEntity<NexTripDeparture[]> tripEntity = restTemplate.getForEntity(template.expand(uriVariables), NexTripDeparture[].class);
        NexTripDeparture[] trips = tripEntity.getBody();

        if (trips == null || trips.length == 0) {
            throw new BusinessException(new BusinessExceptionMessage(ERROR_NOBUS_CODE, ERROR_NOBUS_DESC));
        }

        NextBusResponse response = new NextBusResponse();
        response.setNextDeparture(trips[0].getDepartureText());
        response.setDirection(nextBusRequest.getDirection());
        response.setRoute(nextBusRequest.getRoute());
        response.setStop(nextBusRequest.getStop());

        return response;
    }

}
