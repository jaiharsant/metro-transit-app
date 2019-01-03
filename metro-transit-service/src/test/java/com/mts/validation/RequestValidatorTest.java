/*
 * Copyright 2018 abc, Inc
 * abc Internal Use Only
 */


package com.mts.validation;


import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;

import com.mts.controller.request.NextBusRequest;
import com.mts.exception.BusinessException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;


@Execution(ExecutionMode.CONCURRENT)
public class RequestValidatorTest {

    /*
     * Sets the tests up.
     */
    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testFileExist() {
        assertTrue(RequestValidator.isFileFound("application_test.properties"));
    }

    @Test
    public void testInputStreamFromClassPath() throws IOException {
        assertNotNull(RequestValidator.getInputStreamFromClassPath("application_test.properties"));
    }

    @Test
    public void testloadJsonFromStream() throws IOException {
        InputStream inputStream = RequestValidator.getInputStreamFromClassPath("validation/nextbus.json");
        assertNotNull(RequestValidator.loadJsonFromStream(inputStream));
    }

    @Test
    public void testValidateExp() throws IOException {
        Assertions.assertThrows(BusinessException.class, () -> {
            InputStream inputStream = RequestValidator.getInputStreamFromClassPath("validation/nextbus.json");
            RequestValidator requestValidator = new RequestValidator();
            requestValidator.validate("validation/nextbus.json", inputStream, NextBusRequest.class);
        });
    }

    @Test
    public void testValidate() throws IOException {
        String input = "{\"route\":\"METRO Blue Line\",\"stop\":\"Mall of America Station\",\"direction\":\"NORTH\"}";
        InputStream inputStream = IOUtils.toInputStream(input);
        RequestValidator requestValidator = new RequestValidator();
        NextBusRequest req = requestValidator.validate("validation/nextbus.json", inputStream, NextBusRequest.class);
        assertNotNull(req);
    }

}
