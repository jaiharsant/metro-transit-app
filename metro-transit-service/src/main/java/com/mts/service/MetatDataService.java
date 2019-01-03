

package com.mts.service;


import com.mts.domain.Route;
import com.mts.domain.TextValuePair;


public interface MetatDataService {

    /**
     * Get route details from Cache
     * 
     * @param routeDesc e.g "METRO Blue Line"
     * @return
     */
    Route getRoutes(String routeDesc);

    /**
     * @param routeId e.g "901"
     * @param directionDesc e.g "NORTHBOUND"
     * @return
     */
    TextValuePair getRouteDirections(String routeId, String directionDesc);

    /**
     * Get Stop details for the given route, direction
     * 
     * @param routeId e.g "901"
     * @param directionId e.g "4"
     * @param stopDesc e.g "Mall of America Station"
     * @return
     */
    TextValuePair getRouteDirectionStops(String routeId, String directionId, String stopDesc);
}
