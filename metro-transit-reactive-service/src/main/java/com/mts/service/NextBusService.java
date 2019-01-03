

package com.mts.service;


import com.mts.controller.request.NextBusRequest;
import com.mts.controller.response.NextBusResponse;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;


@Component
public interface NextBusService {
    Mono<NextBusResponse> getNextRouteBus(NextBusRequest nextBusRequest);
}
