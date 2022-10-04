package com.rewards.program.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rewards.program.dtos.ErrorDTO;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler 
  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<ErrorDTO> handleConflict( RuntimeException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setMessage("Illegal Argument or State");
        error.setTime(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT);
        error.setErrorDetail(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDTO error = new ErrorDTO();
        error.setMessage("Request not supported");
        error.setTime(LocalDateTime.now());
        error.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
        error.setErrorDetail(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @ExceptionHandler(value = { Exception.class})
    protected ResponseEntity<ErrorDTO> handleRuntimeException(Exception ex) {
        ErrorDTO error = new ErrorDTO();
        error.setMessage("An exception has occurred");
        error.setTime(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setErrorDetail(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }  
}
