package com.example.notebook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("MR1000", "User cannot be found"),
    USERNAME_OR_EMAIL_ALREADY_EXISTS("MR1001", "Username or email already exists"),
    RESET_TOKEN_NOT_FOUND("MR1002", "Reset token not found"),
    RESET_TOKEN_EXPIRED("MR1003", "Reset token expired"),
    PASSWORD_DOES_NOT_MATCH("MR1003", "Password does not match"),
    GENERAL_ERROR("MR9999", "General error"), NOTE_NOT_FOUND("MR1004","Note cannot be found" ),
    USER_NOT_AUTHORIZED("MR1008","User is not authorized" );

    private final String code;
    private final String message;

}
