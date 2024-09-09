package com.example.notebook.exception;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Generated
public class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private String errorCode;
    private Integer status;

    public static ApiError getApiError(String message, String errorCode, Integer status) {
        return ApiError.builder()
                .message(message)
                .errorCode(errorCode)
                .status(status)
                .build();
    }

}
