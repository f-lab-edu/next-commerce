package org.example.nextcommerce.common.exception;

import org.example.nextcommerce.common.utils.errormessage.ErrorCode;

public class NotFoundException extends ErrorCodeExcpetion {
    public NotFoundException(){    }
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(ErrorCode errorCode){super(errorCode);}


}
