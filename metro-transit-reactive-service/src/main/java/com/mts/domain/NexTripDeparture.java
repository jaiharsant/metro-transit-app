

package com.mts.domain;


import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;


@Data
public class NexTripDeparture {

    @JsonAlias("Actual")
    Boolean actual;

    @JsonAlias("BlockNumber")
    Integer blockNumber;


    @JsonAlias("DepartureText")
    String departureText;

    // @JsonIgnore
    @JsonAlias("DepartureTime")
    String departureTime;

    @JsonAlias("Description")
    String description;

    @JsonAlias("Gate")
    String gate;

    @JsonAlias("Route")
    String route;

    @JsonAlias("RouteDirection")
    String routeDirection;

    @JsonAlias("Terminal")
    String terminal;

    @JsonAlias("VehicleHeading")
    Integer vehicleHeading;

    @JsonAlias("VehicleLatitude")
    Integer vehicleLatitude;

    @JsonAlias("VehicleLongitude")
    Integer vehicleLongitude;
}
