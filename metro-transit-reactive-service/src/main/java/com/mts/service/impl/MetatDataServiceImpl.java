

package com.mts.service.impl;


import static com.mts.common.MetroTransisConstants.*;
import static org.springframework.http.MediaType.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.mts.domain.Route;
import com.mts.domain.TextValuePair;
import com.mts.service.MetatDataService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriTemplate;

import reactor.core.publisher.Mono;



@Service
public class MetatDataServiceImpl implements MetatDataService {

    @Value("${metro.baseUrl}")
    private String baseUrl = "http://svc.metrotransit.org";

    @Value("${metro.routes.url}")
    private String routesUrl = "/NexTrip/Routes";

    @Value("${metro.directions.url}")
    private String directionsUrl = "/NexTrip/Directions/{route}";

    @Value("${metro.stops.url}")
    private String stopsUrl = "/NexTrip/Stops/{route}/{direction}";

    WebClient client = WebClient.create();

    /**
     * Get route details from Cache
     * 
     * @param routeDesc e.g "METRO Blue Line"
     * @return
     */
    @Override
    public Mono<Route> getRoutes(final String routeDesc) {
        return client.get().uri(baseUrl + routesUrl).accept(APPLICATION_JSON).exchange()
                     .flatMap(response -> response.bodyToMono(Route[].class)).map(routeList -> getMatchRoute(routeList, routeDesc));
    }

    private Route getMatchRoute(Route[] routeList, String routeDesc) {
        Optional<Route> routeResult = Arrays.asList(routeList).stream().filter(route -> route.getDescription().equals(routeDesc)).findFirst();

        if (routeResult.isEmpty()) {
            throw new RuntimeException("Route is not found");
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
    public Mono<TextValuePair> getRouteDirections(final String routeId, final String directionDesc) {

        UriTemplate uriTemplate = new UriTemplate(baseUrl + directionsUrl);
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(URI_ROUTE, routeId);

        return client.get().uri(uriTemplate.expand(uriVariables)).accept(APPLICATION_JSON).exchange()
                     .flatMap(response -> response.bodyToMono(TextValuePair[].class))
                     .map(directionList -> getMatchRouteDirections(directionList, routeId, directionDesc));

    }

    private TextValuePair getMatchRouteDirections(TextValuePair[] directions, String routeId, String directionDesc) {
        Optional<TextValuePair> dirsResult = Arrays.asList(directions).stream().filter(direction -> direction.getText().equals(directionDesc)).findFirst();

        if (dirsResult.isEmpty()) {
            throw new RuntimeException("Direction is not found");
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
    public Mono<TextValuePair> getRouteDirectionStops(final String routeId, final String directionId, final String stopDesc) {

        UriTemplate uriTemplate = new UriTemplate(baseUrl + stopsUrl);
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(URI_ROUTE, routeId);
        uriVariables.put(URI_DIRECTION, directionId);

        return client.get().uri(uriTemplate.expand(uriVariables)).header("Content-Type", "application/json").accept(APPLICATION_JSON).exchange()
                     .flatMap(response -> response.bodyToMono(TextValuePair[].class))
                     .map(stopList -> getMatchStopRouteDirections(stopList, routeId, directionId, stopDesc));

    }

    private TextValuePair getMatchStopRouteDirections(TextValuePair[] stops, String routeId, String directionId, String stopDesc) {
        Optional<TextValuePair> stopsResult = Arrays.asList(stops).stream().filter(stop -> stop.getText().equals(stopDesc)).findFirst();

        if (stopsResult.isEmpty()) {
            throw new RuntimeException("Stop Not Found");
        }
        return stopsResult.get();
    }


}
