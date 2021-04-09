package com.bud.weather.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class ErrorMessageDto implements Serializable {
    private static final long serialVersionUID = -7801793605586860786L;
    private int statusCode;
    private Date timestamp;
    private String message;

    public ErrorMessageDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.timestamp = new Date();
        this.message = message;
    }
}
