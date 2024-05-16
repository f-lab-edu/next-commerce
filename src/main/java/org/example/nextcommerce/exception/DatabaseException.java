package org.example.nextcommerce.exception;

import lombok.ToString;
import org.example.nextcommerce.utils.errormessage.ErrorCode;

public class DatabaseException extends ErrorCodeExcpetion{
    public DatabaseException(){}
    public DatabaseException(String message){ super(message);}
    public DatabaseException(ErrorCode errorCode){super(errorCode);}
}
