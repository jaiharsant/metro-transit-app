

package com.mts.domain;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class VehicleLocation {

    @JsonAlias("Bearing")
    Integer bearing;

    @JsonAlias("BlockNumber")
    Integer blockNumber;

    @JsonAlias("Direction")
    Integer direction;

    @JsonIgnore
    @JsonAlias("LocationTime")
    LocalDate locationTime;

    @JsonAlias("Odometer")
    Integer odometer;

    @JsonAlias("Route")
    String route;

    @JsonAlias("Speed")
    Integer speed;

    @JsonAlias("Terminal")
    String terminal;

    @JsonAlias("VehicleLatitude")
    Double vehicleLatitude;

    @JsonAlias("VehicleLongitude")
    Double vehicleLongitude;
}
