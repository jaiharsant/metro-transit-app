

package com.mts.controller;


import static com.mts.common.MetroTransisConstants.*;

import java.io.IOException;
import java.io.InputStream;

import com.mts.controller.request.NextBusRequest;
import com.mts.controller.response.NextBusResponse;
import com.mts.service.NextBusService;
import com.mts.validation.RequestValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(API_TRIPS)
public class NextBusServiceController {

    @Autowired
    private NextBusService metroTransitService;

    @Autowired
    RequestValidator requestValidator;

    @PostMapping(API_NEXTBUS)
    public NextBusResponse getNextRouteBus(InputStream input) throws IOException {
        return metroTransitService.getNextRouteBus(requestValidator.validate("validation/nextbus.json", input, NextBusRequest.class));
    }
}
