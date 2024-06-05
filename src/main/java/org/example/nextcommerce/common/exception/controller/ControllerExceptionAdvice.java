package org.example.nextcommerce.common.exception.controller;

import org.example.nextcommerce.common.exception.*;
import org.example.nextcommerce.common.exception.controller.response.ExceptionResponse;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> defaultException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ErrorCode.UnknownError.getCode(), e.getMessage()));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ExceptionResponse> databaseException(DatabaseException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(e.getErrorCode().getCode(), e.getErrorCode().getDescription()));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ExceptionResponse> memberNotFoundException(MemberNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getErrorCode().getCode(), e.getErrorCode().getDescription()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getErrorCode().getCode(), e.getErrorCode().getDescription()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getErrorCode().getCode(), e.getErrorCode().getDescription()));
    }

    @ExceptionHandler(FileHandleException.class)
    public ResponseEntity<ExceptionResponse> ioException(FileHandleException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ErrorCode.IOException.getCode(), e.getMessage()));
    }


}
