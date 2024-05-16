package org.example.nextcommerce.exception;

import org.example.nextcommerce.utils.errormessage.ErrorCode;

public class UnauthorizedException extends ErrorCodeExcpetion{
    public UnauthorizedException(){}
    public UnauthorizedException(String message){
        super(message);
    }
    public UnauthorizedException(ErrorCode errorCode){super(errorCode);}
}
