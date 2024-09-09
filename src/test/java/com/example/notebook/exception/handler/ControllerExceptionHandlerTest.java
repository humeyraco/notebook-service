package com.example.notebook.exception.handler;

import com.example.notebook.exception.ApiError;
import com.example.notebook.exception.CommonException;
import com.example.notebook.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @Test
    void handleException_returnsInternalServerError() {
        CommonException exception = CommonException.getCommonException(ErrorCode.GENERAL_ERROR);

        ResponseEntity<ApiError> response = controllerExceptionHandler.handleException(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void handleMethodArgumentNotValid_returnsBadRequest() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        WebRequest request = mock(WebRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Collections.emptyList());

        ResponseEntity<Object> response = controllerExceptionHandler.handleMethodArgumentNotValid(exception, null, HttpStatus.BAD_REQUEST, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}