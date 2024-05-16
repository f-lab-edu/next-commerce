package org.example.nextcommerce.exception;

import org.example.nextcommerce.utils.errormessage.ErrorCode;

public class MemberNotFoundException extends NotFoundException{
    public MemberNotFoundException(){ }
    public MemberNotFoundException(String message){super(message);}
    public MemberNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }

}
