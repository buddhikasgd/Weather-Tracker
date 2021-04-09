package com.bud.weather.api.exception;

public class ValidationException extends RuntimeException{
    private static final long serialVersionUID = 7847980931945038663L;
    public ValidationException(String errorMessage) {
        super(errorMessage);
    }
}
