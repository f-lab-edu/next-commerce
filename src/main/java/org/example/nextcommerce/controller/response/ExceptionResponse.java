package org.example.nextcommerce.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExceptionResponse {

    int code;
    String message;

    public ExceptionResponse(int code, String message){
        super();
        this.code = code;
        this.message = message;
    }

}
