/*
 * Copyright 2018 Apple, Inc
 * Apple Internal Use Only
 */


package com.mts.exception;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    private List<BusinessExceptionMessage> errorMessageList;
    private String errorId;
    
    public ErrorResponse(final BusinessExceptionMessage errorMessage) {
        this.errorMessageList = Collections.singletonList(errorMessage);
        this.errorId = UUID.randomUUID().toString();
    }

    public ErrorResponse(final List<BusinessExceptionMessage> errorMessageList) {
        this.errorMessageList = errorMessageList;
        this.errorId = UUID.randomUUID().toString();
    }

    public ErrorResponse(String errorCode) {
        this.errorId = errorCode;
    }

    @JsonProperty("errors")
    public List<BusinessExceptionMessage> getErrorMessage() {
        return errorMessageList;
    }

    @JsonProperty("errorId")
    public String getErrorId() {
        return errorId;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
