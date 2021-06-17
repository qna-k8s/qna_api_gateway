package com.qna.edu.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String message){
        super(message);
    }

}
