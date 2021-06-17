package com.qna.edu.util;

import java.util.Base64;
import java.util.Optional;

import com.qna.edu.JpaRepositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Authentication {
    @Autowired
    private UserRepository userRepository;
    public Authentication(){

    }
    public <T> T authenticate(String authHeader, Class<T> type){
        if(authHeader==null||!authHeader.startsWith("Basic "))
            throw new InvalidCredentialsException("Not authourized!!");
        String data = new String(Base64.getDecoder().decode(authHeader.split(" ")[1]));
        String[] fields=  data.split(":");
        if(fields.length<2){
            throw new InvalidCredentialsException("Username and Password are required!!");
        }
        Optional<T> userOptional = userRepository.findByUsernameAndPassword(fields[0], fields[1], type);
        if(!userOptional.isPresent()){
            throw new InvalidCredentialsException("Not authourized!!");
        }

        return userOptional.get();
    }
}
