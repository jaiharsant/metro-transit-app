/*
 * Copyright 2018 abc, Inc
 * abc Internal Use Only
 */


package com.mts.controller;


import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.mts.spring.MetroTransitServiceConfigTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@Tag("integration")
@SpringJUnitConfig(classes = { MetroTransitServiceConfigTest.class })
public class NextBusServiceControllerTest {

    private HttpHeaders httpHeaders;

    @Autowired
    NextBusServiceController nextBusServiceController;

    /**
     * Sets the tests up.
     */
    @BeforeEach
    public void setUp() {
        httpHeaders = mock(HttpHeaders.class);
    }

    @Test
    public void testNextBus() throws IOException {
        String input = "{\"route\":\"METRO Blue Line\",\"stop\":\"Mall of America Station\",\"direction\":\"NORTH\"}";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getInputStream()).thenReturn(getInputStream(input));

        nextBusServiceController.getNextRouteBus(request.getInputStream());
    }


    /**
     * Prepare MOCK inputStream
     * 
     * @param input
     * @return
     */
    private ServletInputStream getInputStream(String input) {
        ServletInputStream servletInputStream = null;
        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes("UTF-8"));
            servletInputStream = new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }
            };
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return servletInputStream;
    }
}
