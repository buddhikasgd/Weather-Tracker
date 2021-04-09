package com.bud.weather.api.exception;

public class LocationNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -3395609489802455677L;
    public LocationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
