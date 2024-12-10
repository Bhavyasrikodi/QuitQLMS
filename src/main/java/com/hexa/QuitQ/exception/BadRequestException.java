package com.hexa.QuitQ.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private HttpStatus status; 
    private String message;

    public BadRequestException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    public BadRequestException(HttpStatus status, String message, String additionalMessage) {
        super(message); 
        this.status = status;
        this.message = additionalMessage;
    }

    public BadRequestException() {
        super();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message != null ? message : super.getMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
