package org.example.nextcommerce.common.exception;

import org.example.nextcommerce.common.utils.errormessage.ErrorCode;

public class FileHandleException extends ErrorCodeExcpetion{
    public FileHandleException(){}
    public FileHandleException(String message){super(message);}
    public FileHandleException(ErrorCode errorCode){super(errorCode);}
}
