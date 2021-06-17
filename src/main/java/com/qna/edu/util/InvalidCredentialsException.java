package com.qna.edu.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){}
    public InvalidCredentialsException(String message){
        super(message);
    }
}
