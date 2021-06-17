package com.qna.edu.Responses;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ErrorResponse {
    private String status;
    private String message;
    public ErrorResponse() {
    }
    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
}
