package com.bud.weather.api.exception;

import com.bud.weather.api.dto.ErrorMessageDto;
import com.bud.weather.api.util.ErrorConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class WeatherExceptionController {
    public static final Logger logger = LoggerFactory.getLogger(WeatherExceptionController.class);
    private ErrorMessageDto errorMessageDto;

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessageDto ValidationException(ValidationException exception) {
        logger.error(exception.getMessage());
        return new ErrorMessageDto(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage());
    }

    @ExceptionHandler(value = LocationNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto LocationNotFoundException(LocationNotFoundException exception) {
        logger.error(exception.getMessage());
        return new ErrorMessageDto(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto HttpClientErrorException(HttpClientErrorException exception) {
        logger.error("Open weather api error {}", exception.getMessage());
        return new ErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorConstant.COUNTRY_OR_CITY_NOT_FOUND);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessageDto ConstraintViolationException(ConstraintViolationException exception) {
        logger.error("Validation error {}", exception.getMessage());
        return new ErrorMessageDto(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage());
    }

    @ExceptionHandler(value = TooManyRequestsException.class)
    @ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
    public ErrorMessageDto TooManyRequestsException(TooManyRequestsException exception) {
        logger.error("Too many requests {}", exception.getMessage());
        return new ErrorMessageDto(HttpStatus.TOO_MANY_REQUESTS.value(), ErrorConstant.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(value = MissingHeaderException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto MissingHeaderException(MissingHeaderException exception) {
        logger.error("Header is missing {}", exception.getMessage());
        return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
}
