package com.stokessoftwaresolutions.checkbookapi.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorDetails> handlePermissionDeniedException(PermissionDeniedException permissionDeniedException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), permissionDeniedException.getMessage(), webRequest.getDescription(false), "PERMISSION_DENIED");
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> sb.append(error.getDefaultMessage() + "\n"));
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), sb.toString(), request.getDescription(false), "VALIDATION_ERROR");
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
