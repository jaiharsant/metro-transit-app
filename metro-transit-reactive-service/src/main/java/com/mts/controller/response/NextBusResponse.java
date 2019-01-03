

package com.mts.controller.response;


import java.io.Serializable;

import lombok.Data;


@Data
public class NextBusResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2866960949806698905L;

    String route;
    String stop;
    String direction;
    String nextDeparture;
}
