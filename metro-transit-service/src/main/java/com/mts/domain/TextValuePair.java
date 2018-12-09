/*
 * Copyright 2018 Apple, Inc
 * Apple Internal Use Only
 */


package com.mts.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class TextValuePair {
    
    @JsonAlias("Decription")
    String decription;
    
    @JsonAlias("Text")
    String text;
    
    @JsonAlias("Value")
    String value;
    
}
