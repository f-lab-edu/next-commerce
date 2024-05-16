package org.example.nextcommerce.exception;

import org.example.nextcommerce.utils.errormessage.ErrorCode;
import org.springframework.data.crossstore.ChangeSetPersister;

public class NotFoundException extends ErrorCodeExcpetion {
    public NotFoundException(){    }
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(ErrorCode errorCode){super(errorCode);}


}
