package com.bud.weather.api.exception;

public class ServiceOutageException extends RuntimeException{
    private static final long serialVersionUID = 6354264472885714208L;
    public ServiceOutageException(String errorMessage) {
        super(errorMessage);
    }
}
