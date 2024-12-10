package com.hexa.QuitQ.exception;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler{
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exceptionObj,WebRequest w){
		ErrorDetails e = new ErrorDetails(LocalDateTime.now(),exceptionObj.getMessage(),w.getDescription(false),"RESOURCE_NOT_FOUND");
		return ResponseEntity.ok(e);
	}
	
	@ExceptionHandler(CRUDAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(CRUDAPIException exception, WebRequest webRequest) {
	    ErrorDetails errorDetails = new ErrorDetails(
	            LocalDateTime.now(), 
	            exception.getMessage(),
	            webRequest.getDescription(false),
	            "CRUDAPI_EXCEPTION" 
	    );
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// Global exceptions
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {
	    ErrorDetails errorDetails = new ErrorDetails(
	            LocalDateTime.now(), 
	            exception.getMessage(),
	            webRequest.getDescription(false),
	            "GLOBAL_EXCEPTION"
	    );
	    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest) {
	    ErrorDetails errorDetails = new ErrorDetails(
	            LocalDateTime.now(), 
	            exception.getMessage(),
	            webRequest.getDescription(false),
	            "ACCESS_DENIED"
	    );
	    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}
 
	
	@ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateEmailException(DuplicateEmailException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), "DUPLICATE_EMAIL");
        return ResponseEntity.ok(errorDetails);
    }
	
	@ExceptionHandler(ResourceDeletionException.class)
    public ResponseEntity<ErrorDetails> handleResourceDeletionException(ResourceDeletionException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), request.getDescription(false), "RESOURCE_DELETION_ERROR");
        return ResponseEntity.ok(errorDetails);
    }

    @ExceptionHandler(InvalidResourceDataException.class)
    public ResponseEntity<ErrorDetails> handleInvalidCustomerDataException(InvalidResourceDataException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), request.getDescription(false), "INVALID_RESOURCE_DATA");
        return ResponseEntity.ok(errorDetails);
    }
    
    @ExceptionHandler(ProductUpdateException.class)
    public ResponseEntity<ErrorDetails> handleProductUpdateException(ProductUpdateException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), "PRODUCT_UPDATE_FAILED");
        return ResponseEntity.ok(errorDetails);
    }
	
 // Handle specific exception: OrderCancellationException
    @ExceptionHandler(OrderCancellationException.class)
    public ResponseEntity<ErrorDetails> handleOrderCancellationException(OrderCancellationException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), "ORDER_CANCELLATION_FAILED");
        return ResponseEntity.ok(errorDetails);
    }
    
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String,String> errors = new HashMap();
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
		errorList.forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "An unexpected error occurred.",
            request.getDescription(false),
            "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    
}
