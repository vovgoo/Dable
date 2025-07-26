package com.vovgoo.demo.exceptions;

public class RegistrationInProgressException extends RuntimeException {
    public RegistrationInProgressException(String message) {
        super(message);
    }
}
