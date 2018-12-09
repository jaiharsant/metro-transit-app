/*
 * Copyright 2018 Apple, Inc
 * Apple Internal Use Only
 */


package com.mts.service;

import com.mts.controller.request.NextBusRequest;

import org.springframework.stereotype.Component;

@Component
public interface NextBusService {
    String getNextRouteBus(NextBusRequest nextBusRequest);
}
