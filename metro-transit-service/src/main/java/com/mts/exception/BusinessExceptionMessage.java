/*
 * Copyright 2018 Apple, Inc
 * Apple Internal Use Only
 */


package com.mts.exception;

public class BusinessExceptionMessage {

    private String code;
    private String message;


    public BusinessExceptionMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "BusinessExceptionMessage [code=" + code + ", message=" + message + "]";
    }
}
