package org.example.nextcommerce.common.exception;

import org.example.nextcommerce.common.utils.errormessage.ErrorCode;

public class MemberNotFoundException extends NotFoundException{
    public MemberNotFoundException(){ }
    public MemberNotFoundException(String message){super(message);}
    public MemberNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }

}
