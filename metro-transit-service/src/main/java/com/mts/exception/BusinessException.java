/*
 * Copyright 2018 Apple, Inc
 * Apple Internal Use Only
 */


package com.mts.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;
    
    BusinessExceptionMessage businessExceptionMessage;
    HttpStatus httpStatus;

    BusinessException(){
        super();
    }
    
    public BusinessException(BusinessExceptionMessage businessException){
        this.businessExceptionMessage = businessException;
    }
    
    BusinessException(BusinessExceptionMessage businessException, HttpStatus httpStatus){
        this.businessExceptionMessage = businessException;
        this.httpStatus = httpStatus;
    }

    
}
