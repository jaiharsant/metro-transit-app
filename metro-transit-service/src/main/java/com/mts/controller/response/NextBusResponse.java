

package com.mts.controller.response;


import lombok.Data;


@Data
public class NextBusResponse {
    String route;
    String stop;
    String direction;
    String nextDeparture;
}
