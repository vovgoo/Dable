package com.vovgoo.demo.exceptions;

public class JsonProcessingExceptionWrapper extends RuntimeException {
    public JsonProcessingExceptionWrapper(String message, Throwable cause) {
        super(message, cause);
    }
}