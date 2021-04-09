package com.bud.weather.api.exception;

public class MissingHeaderException extends RuntimeException{
    private static final long serialVersionUID = 4021806347915923899L;
    public MissingHeaderException(String errorMessage) {
        super(errorMessage);
    }
}
