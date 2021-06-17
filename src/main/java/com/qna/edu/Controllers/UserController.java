package com.qna.edu.Controllers;

import java.sql.Timestamp;
import java.util.Optional;

import com.qna.edu.Entities.User;
import com.qna.edu.JpaRepositories.UserRepository;
import com.qna.edu.Projections.UserView;
import com.qna.edu.Responses.UserSuccessResponse;
import com.qna.edu.ValidationGroups.OnCreate;
import com.qna.edu.ValidationGroups.OnUpdate;
import com.qna.edu.util.Authentication;
import com.qna.edu.util.InvalidRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Authentication authenticationService;

    @PostMapping(value = "")
    public ResponseEntity<UserSuccessResponse> registerUser(@Validated(OnCreate.class) @RequestBody User user){
        Timestamp time= new Timestamp(System.currentTimeMillis());
        user.setAccountCreated(time);
        user.setAccountUpdated(time);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new UserSuccessResponse(
            user.getId(), user.getFirstName(), user.getLastName(),user.getUsername(), user.getAccountCreated(), user.getAccountUpdated()
            ));
    }
    
    @GetMapping(value = "/self")
    public ResponseEntity<Object> getAuthUserProfile(@RequestHeader("authorization") String authorizationHeader){
        UserView user = authenticationService.authenticate(authorizationHeader,UserView.class);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @PutMapping(value = "/self")
    public ResponseEntity<UserSuccessResponse> updateUserProfile(@RequestHeader("authorization") String authorizationHeader, 
    @Validated(OnUpdate.class) @RequestBody User dataToUpdate){
        User user = authenticationService.authenticate(authorizationHeader,User.class);
        if(dataToUpdate.getFirstName()!=null){
            user.setFirstName(dataToUpdate.getFirstName());
        }
        if(dataToUpdate.getLastName()!=null){
            user.setLastName(dataToUpdate.getLastName());
        }
        if(dataToUpdate.getUsername()!=null){
            user.setUsername(dataToUpdate.getUsername());
        }
        if(dataToUpdate.getPassword()!=null){
            user.setPassword(dataToUpdate.getPassword());
        }
        user.setAccountUpdated(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new UserSuccessResponse(
            user.getId(), user.getFirstName(), user.getLastName(),user.getUsername(), user.getAccountCreated(), user.getAccountUpdated()
            ));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "id") Long id){
        Optional<UserView> optionalUser= userRepository.findById(id,UserView.class);
        if(optionalUser.isPresent()){
            UserView user= optionalUser.get();
            System.out.println(user.getQuestions().size()+"SIZE-----------");
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        else{
            throw new InvalidRequestException("User not found!!");
        }
    }
}
