

package com.mts.domain;


import lombok.Data;


@Data
public class RouteDirection {
    Route route;
    TextValuePair direction;
    TextValuePair stop;
}
