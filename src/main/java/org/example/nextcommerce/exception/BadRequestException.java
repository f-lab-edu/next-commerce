package org.example.nextcommerce.exception;

import org.example.nextcommerce.utils.errormessage.ErrorCode;

public class BadRequestException extends ErrorCodeExcpetion{
    public BadRequestException(){}
    public BadRequestException(String message){
        super(message);
    }
    public BadRequestException(ErrorCode errorCode){super(errorCode);}


}
