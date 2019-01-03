

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
