package org.example.nextcommerce.controller;

import org.example.nextcommerce.controller.response.ExceptionResponse;
import org.example.nextcommerce.exception.DatabaseException;
import org.example.nextcommerce.exception.MemberNotFoundException;
import org.example.nextcommerce.exception.UnauthorizedException;
import org.example.nextcommerce.utils.errormessage.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> defaultException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ErrorCode.UnknownError.getCode(), ErrorCode.UnknownError.getDescription()));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ExceptionResponse> databaseException(DatabaseException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(e.getErrorCode().getCode(), e.getErrorCode().getDescription()));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ExceptionResponse> memberNotFoundException(MemberNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getErrorCode().getCode(), e.getErrorCode().getDescription()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidExceptions(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(
                                ( (FieldError) c).getField(), c.getDefaultMessage()
                        )
                );
        return ResponseEntity.status(e.getStatusCode()).body(new ExceptionResponse(ErrorCode.InvalidRequestContent.getCode(), ErrorCode.InvalidRequestContent.getDescription(), errors));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getErrorCode().getCode(), e.getErrorCode().getDescription()));
    }


}
