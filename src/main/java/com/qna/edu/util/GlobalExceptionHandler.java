package com.qna.edu.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.qna.edu.Responses.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.context.request.WebRequest;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ApplicationContext context;
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);   
    }

    @ExceptionHandler(InvalidRequestException.class)
    protected ResponseEntity<Object> handleInvalidRequest(InvalidRequestException ex){
        ErrorResponse errorResponse = context.getBean(ErrorResponse.class);
        errorResponse.setStatus("failed");
        errorResponse.setMessage(ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler({MissingRequestHeaderException.class,InvalidCredentialsException.class})
    protected ResponseEntity<Object> handleMissingHeaderException(Exception ex){
        ErrorResponse errorResponse = context.getBean(ErrorResponse.class);
        errorResponse.setStatus("failed");
        errorResponse.setMessage("Unauthorized to make this request");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    @ExceptionHandler( {TransactionSystemException.class} )
    public ResponseEntity<Object> handleTransactionSystemException(Exception ex, WebRequest request) {
        Throwable cause =  ((TransactionSystemException)ex).getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> hs = ((ConstraintViolationException) cause).getConstraintViolations();
            Map<Object,String> errorMap =  new HashMap<>();
            errorMap.put("status","failed");
            for(ConstraintViolation<?> violation : hs){
                errorMap.put(violation.getPropertyPath(),violation.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }
        ErrorResponse errorResponse = context.getBean(ErrorResponse.class);
        errorResponse.setStatus("failed");
        errorResponse.setMessage("Internal server error!!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(Exception ex){
        Throwable cause =  ((DataIntegrityViolationException)ex).getRootCause();
        System.out.println("Hello"+cause);
        if (cause instanceof SQLIntegrityConstraintViolationException) {
            // Set<ConstraintViolation<?>> hs = ((SQLIntegrityConstraintViolationException) cause).getConstraintViolations();
            Map<Object,String> errorMap =  new HashMap<>();
            errorMap.put("status","failed");
            errorMap.put("message","Duplicate entries found!!");
            // for(ConstraintViolation<?> violation : hs){
            //     errorMap.put(violation.getPropertyPath(),violation.getMessage());
            // }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }
        ErrorResponse errorResponse = context.getBean(ErrorResponse.class);
        errorResponse.setStatus("failed");
        errorResponse.setMessage("Internal server error!!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(NotFoundException ex){
        ErrorResponse errorResponse = context.getBean(ErrorResponse.class);
        errorResponse.setStatus("failed");
        errorResponse.setMessage(ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}