package com.miniautorizador.data.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final Object body;

    public CustomException(HttpStatus status, Object body) {
        super(status.getReasonPhrase());
        this.status = status;
        this.body = body;
    }
}
