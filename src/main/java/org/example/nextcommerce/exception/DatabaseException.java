package org.example.nextcommerce.exception;

import lombok.ToString;
import org.example.nextcommerce.utils.errormessage.ErrorCode;

public class DatabaseException extends RuntimeException{
    public DatabaseException(){}
    public DatabaseException(String message){ super(message);}

}
