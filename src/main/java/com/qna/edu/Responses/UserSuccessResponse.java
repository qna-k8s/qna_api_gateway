package com.qna.edu.Responses;

import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class UserSuccessResponse {
    private Long id;
    private String first_name;
    private String last_name;
    private String username;
    private Timestamp account_created;
    private Timestamp account_updated;
    public UserSuccessResponse(Long id, String first_name, String last_name, String username, Timestamp account_created,
            Timestamp account_updated) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.account_created = account_created;
        this.account_updated = account_updated;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Timestamp getAccount_created() {
        return account_created;
    }
    public void setAccount_created(Timestamp account_created) {
        this.account_created = account_created;
    }
    public Timestamp getAccount_updated() {
        return account_updated;
    }
    public void setAccount_updated(Timestamp account_updated) {
        this.account_updated = account_updated;
    }
    
}
