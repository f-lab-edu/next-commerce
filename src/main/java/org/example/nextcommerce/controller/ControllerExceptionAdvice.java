package org.example.nextcommerce.controller;

import org.example.nextcommerce.controller.response.ExceptionResponse;
import org.example.nextcommerce.exception.DatabaseException;
import org.example.nextcommerce.exception.MemberNotFoundException;
import org.example.nextcommerce.utils.errormessage.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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



}
