package com.bud.weather.api.util;

public interface ErrorConstant {
    String INVALID_COUNTRY_OR_CITY = "Country or City is invalid";
    String COUNTRY_OR_CITY_NOT_FOUND = "Unable to find weather data for given country and city.";
    String TOO_MANY_REQUESTS = "Sorry! Rate limit exceed. Please try again later";
    String SERVICE_UNAVAILABLE = "Sorry! Service is unavailable. Please try again later";
}
