
package com.mts.handler;


import static org.springframework.http.MediaType.*;

import com.mts.controller.request.NextBusRequest;
import com.mts.controller.response.NextBusResponse;
import com.mts.service.NextBusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;


@Component
public class NextBusServiceHandler {

    @Autowired
    NextBusService nextBusService;


    public Mono<ServerResponse> getNextTrip(final ServerRequest request) {

        return request.bodyToMono(NextBusRequest.class)
                      .map(req -> nextBusService.getNextRouteBus(req))
                      .flatMap(res -> ServerResponse.ok().contentType(APPLICATION_JSON).body(res, NextBusResponse.class));

    }
}
