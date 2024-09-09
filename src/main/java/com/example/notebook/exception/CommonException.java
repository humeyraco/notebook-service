package com.example.notebook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonException extends RuntimeException {

    private final String message;
    private final String errorCode;
    private final Integer status;

    @Override
    public String getMessage() {
        return String.format(
                " Exception occurred. Message: %s , ErrorCode: %s , HttpStatus %d", message, errorCode, status);
    }
    public static CommonException getCommonException(ErrorCode errorCode) {
        return new CommonException(errorCode.getMessage(), errorCode.getCode(), HttpStatus.BAD_REQUEST.value());
    }
}
