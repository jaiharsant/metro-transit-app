

package com.mts.exception;


import java.util.Collections;
import java.util.List;

import lombok.Data;


@Data
public class SystemException extends RuntimeException {
    private final String errorMessage;
    private final List<SystemException> causingExceptions;

    public SystemException(String errorCodeTxt, List<SystemException> causingExceptions) {
        this.errorMessage = errorCodeTxt;
        this.causingExceptions = causingExceptions;
    }

    public SystemException(String errorCodeTxt) {
        this(errorCodeTxt, Collections.emptyList());
    }
}
