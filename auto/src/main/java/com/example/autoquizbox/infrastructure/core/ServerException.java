package com.example.autoquizbox.infrastructure.core;

public class ServerException extends RuntimeException {

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
