package com.bud.weather.api.exception;

public class TooManyRequestsException extends RuntimeException{
    private static final long serialVersionUID = -2158399736386411003L;
    public TooManyRequestsException(String errorMessage){
        super(errorMessage);
    }
}
