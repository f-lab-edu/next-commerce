package org.example.nextcommerce.exception;

import org.example.nextcommerce.utils.errormessage.ErrorCode;

public class ErrorCodeExcpetion extends RuntimeException{
    private ErrorCode errorCode;
    public ErrorCodeExcpetion(){}
    public ErrorCodeExcpetion(String message){
        super(message);
    }

    public ErrorCodeExcpetion(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
