/*
 * Copyright 2018 Apple, Inc
 * Apple Internal Use Only
 */


package com.mts.controller.request;


import java.io.Serializable;

import lombok.Data;


@Data
public class NextBusRequest implements Serializable {

    String route;
    String stop;
    String direction;
}
