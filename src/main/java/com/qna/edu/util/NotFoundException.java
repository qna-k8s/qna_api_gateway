package com.qna.edu.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NotFoundException extends RuntimeException {
    public NotFoundException(){};
    public NotFoundException(String message){
        super(message);
    }
}
