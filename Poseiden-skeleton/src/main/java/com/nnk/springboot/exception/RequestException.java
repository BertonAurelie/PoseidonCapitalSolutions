package com.nnk.springboot.exception;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
}
