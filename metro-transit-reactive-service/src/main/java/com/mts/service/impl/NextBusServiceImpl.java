package com.mts.service.impl;


import static com.mts.common.MetroTransisConstants.*;
import static org.springframework.http.MediaType.*;

import java.util.HashMap;
import java.util.Map;

import com.mts.controller.request.NextBusRequest;
import com.mts.controller.response.NextBusResponse;
import com.mts.domain.*;
import com.mts.service.MetatDataService;
import com.mts.service.NextBusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriTemplate;

import reactor.core.publisher.Mono;


@Service
public class NextBusServiceImpl implements NextBusService {

    @Autowired
    MetatDataService metatDataService;

    @Value("${metro.baseUrl}")
    String baseUrl;

    @Value("${metro.nextTrip.url}")
    String nextTripUrl;

    WebClient client = WebClient.create();

    private Mono<NextBusResponse> getTrips(RouteDirection routeDirection, final NextBusRequest nextBusRequest) {
        UriTemplate template = new UriTemplate(baseUrl + nextTripUrl);
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(URI_ROUTE, routeDirection.getRoute().getRoute());
        uriVariables.put(URI_DIRECTION, routeDirection.getDirection().getValue());
        uriVariables.put(URI_STOP, routeDirection.getStop().getValue());

        Mono<NexTripDeparture[]> results =
                    client.get().uri(template.expand(uriVariables)).header("Content-Type", "application/json").accept(APPLICATION_JSON).exchange()
                          .flatMap(response -> response.bodyToMono(NexTripDeparture[].class));

        return results.map(tripsList -> getNextBusResponse(tripsList, nextBusRequest));
    }

    /**
     * Get next trip details
     * 
     * @param nextBusRequest
     * @return
     */
    @Override
    public Mono<NextBusResponse> getNextRouteBus(final NextBusRequest nextBusRequest) {

        Mono<Route> routeMono = metatDataService.getRoutes(nextBusRequest.getRoute()).cache();

        final Mono<TextValuePair> directionMono = routeMono.flatMap((routeObj) -> {
            return metatDataService.getRouteDirections(routeObj.getRoute(),
                        Direction.valueOf(nextBusRequest.getDirection().toUpperCase()).getText());
        }).onErrorMap(error -> connectorToBusinessException(error));


        final Mono<TextValuePair> stopMono = Mono.zip(routeMono, directionMono).flatMap(zippedResponse -> {
            RouteDirection routeDirection1 = new RouteDirection();
            routeDirection1.setDirection(zippedResponse.getT2());
            routeDirection1.setRoute(zippedResponse.getT1());

            return metatDataService.getRouteDirectionStops(routeDirection1.getRoute().getRoute(), routeDirection1.getDirection().getValue(),
                        nextBusRequest.getStop());
        });


        return Mono.zip(routeMono, directionMono, stopMono).flatMap(zippedResponse -> {
            RouteDirection routeDirection = new RouteDirection();
            routeDirection.setDirection(zippedResponse.getT2());
            routeDirection.setRoute(zippedResponse.getT1());
            routeDirection.setStop(zippedResponse.getT3());
            return getTrips(routeDirection, nextBusRequest);
        }).onErrorMap(error -> connectorToBusinessException(error));
    }

    private NextBusResponse getNextBusResponse(NexTripDeparture[] trips, NextBusRequest nextBusRequest) {
        NextBusResponse response = new NextBusResponse();
        response.setNextDeparture(trips[0].getDepartureText());
        response.setDirection(nextBusRequest.getDirection());
        response.setRoute(nextBusRequest.getRoute());
        response.setStop(nextBusRequest.getStop());
        return response;
    }

    public static RuntimeException connectorToBusinessException(final Throwable throwable) {
        throwable.printStackTrace();
        return new RuntimeException("Exception" + throwable.getCause());
    }

}
