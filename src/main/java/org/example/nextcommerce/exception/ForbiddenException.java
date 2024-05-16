package org.example.nextcommerce.exception;

import org.example.nextcommerce.utils.errormessage.ErrorCode;

public class ForbiddenException extends ErrorCodeExcpetion{
    public ForbiddenException(){    }
    public ForbiddenException(String message){
        super(message);
    }
    public ForbiddenException(ErrorCode errorCode){super(errorCode);}
}

