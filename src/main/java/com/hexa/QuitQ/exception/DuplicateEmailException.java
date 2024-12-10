package com.hexa.QuitQ.exception;

public class DuplicateEmailException extends RuntimeException {
	private String fieldMessage;
    public DuplicateEmailException(String message) {
        super();
        this.fieldMessage=message;
    }
    public String getMessage() {
    	return fieldMessage;
    }
}
