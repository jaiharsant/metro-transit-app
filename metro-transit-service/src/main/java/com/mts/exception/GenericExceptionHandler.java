

package com.mts.exception;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(BusinessException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(ex.getBusinessExceptionMessage());
        HttpStatus derivedstatusCode = (ex.getHttpStatus() == null) ? HttpStatus.BAD_REQUEST : ex.getHttpStatus();
        return new ResponseEntity<>(errorDetails, derivedstatusCode);
    }

    @ExceptionHandler(SystemException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(SystemException ex, WebRequest request) {
        BusinessExceptionMessage exceptionMsg = new BusinessExceptionMessage("SYS_ERROR", ex.getErrorMessage());
        ErrorResponse errorDetails = new ErrorResponse(exceptionMsg);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(Throwable ex, WebRequest request) {
        BusinessExceptionMessage exceptionMsg = new BusinessExceptionMessage("SYS_ERROR", "System error occured, please contact system admin.");

        ex.printStackTrace();

        ErrorResponse errorDetails = new ErrorResponse(exceptionMsg);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
