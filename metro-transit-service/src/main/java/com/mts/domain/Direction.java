/*
 * Copyright 2018 Apple, Inc
 * Apple Internal Use Only
 */


package com.mts.domain;

import lombok.Getter;

public enum Direction {
   
    EAST(1,"EASTBOUND"), WEST(2, "WESTBOUND"), SOUTH(3, "SOUTHBOUND"), NORTH(4, "NORTHBOUND");

    @Getter
    final Integer value;
    
    @Getter
    final String text;
    
    Direction(Integer val, String text){
        this.value = val;
        this.text = text;
    }
}
