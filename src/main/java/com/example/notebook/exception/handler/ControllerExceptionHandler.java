package com.example.notebook.exception.handler;

import com.example.notebook.exception.ApiError;
import com.example.notebook.exception.CommonException;
import com.example.notebook.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CommonException.class)
    public final ResponseEntity<ApiError> handleException(Exception exception) {
        log.error("Unhandled exception occurred!", exception);
        ApiError apiError = ApiError.getApiError(exception.getMessage(), ErrorCode.GENERAL_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        try {
            StringBuilder errorMessages = new StringBuilder();
            for (var error : ex.getBindingResult().getAllErrors()) {
                if (!errorMessages.isEmpty()) {
                    errorMessages.append(", ");
                }
                errorMessages.append(error.getDefaultMessage());
            }
            log.error("Method argument not valid exception occurred! ", ex);
            return new ResponseEntity<>(
                    ApiError.getApiError(errorMessages.toString(), ErrorCode.GENERAL_ERROR.getCode(),
                    HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        } finally {
            MDC.clear();
        }
    }
}
