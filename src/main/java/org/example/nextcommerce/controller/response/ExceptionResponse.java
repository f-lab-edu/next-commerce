package org.example.nextcommerce.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.nextcommerce.utils.errormessage.ErrorCode;

import java.util.Map;

@Getter
@NoArgsConstructor
public class ExceptionResponse {

    int code;
    String message;
    Map<String, String> valid;

    public ExceptionResponse(int code, String message){
        super();
        this.code = code;
        this.message = message;
    }
    public ExceptionResponse(int code, String message, Map<String, String> valid){
        super();
        this.code = code;
        this.message = message;
        this.valid = valid;
    }

}
