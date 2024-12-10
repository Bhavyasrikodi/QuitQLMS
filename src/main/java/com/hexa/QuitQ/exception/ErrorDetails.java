package com.hexa.QuitQ.exception;

import java.sql.Date;
import java.time.LocalDateTime;

public class ErrorDetails {
	private LocalDateTime time;
	private String message;
	private String path;
	private String errorCode;
	public ErrorDetails(LocalDateTime t, String message, String path, String errorCode) {
		super();
		this.time = t;
		this.message = message;
		this.path = path;
		this.errorCode = errorCode;
	}
	
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime t) {
		this.time = t;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		return "ErrorDetails [t=" + time + ", message=" + message + ", path=" + path + ", errorCode=" + errorCode + "]";
	}
	
	
}