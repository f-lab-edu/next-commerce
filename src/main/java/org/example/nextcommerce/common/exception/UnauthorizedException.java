package org.example.nextcommerce.common.exception;

import org.example.nextcommerce.common.utils.errormessage.ErrorCode;

public class UnauthorizedException extends ErrorCodeExcpetion{
    public UnauthorizedException(){}
    public UnauthorizedException(String message){
        super(message);
    }
    public UnauthorizedException(ErrorCode errorCode){super(errorCode);}
}
