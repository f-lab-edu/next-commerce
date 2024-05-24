package org.example.nextcommerce.common.exception;

import org.example.nextcommerce.common.utils.errormessage.ErrorCode;

public class DatabaseException extends ErrorCodeExcpetion{
    public DatabaseException(){}
    public DatabaseException(String message){ super(message);}
    public DatabaseException(ErrorCode errorCode){super(errorCode);}
}
