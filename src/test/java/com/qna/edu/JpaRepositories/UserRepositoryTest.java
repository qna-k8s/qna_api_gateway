package com.qna.edu.JpaRepositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.qna.edu.Entities.User;
import com.qna.edu.Projections.UserView;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;


    @Test
    public void testValidation(){
        Timestamp time= new Timestamp(System.currentTimeMillis());
        User user = new User("Abc", "cde", time, time);
        user.setUsername("abc@example.com");
        user.setPassword("");
        assertThrows(ConstraintViolationException.class, ()->{
            userRepository.save(user);
            testEntityManager.flush();
        });
        user.setPassword("Test@1234");
        user.setUsername("abcd");
        assertThrows(ConstraintViolationException.class, ()->{
            userRepository.save(user);
            testEntityManager.flush();
        });
        user.setUsername("abc@example.com");
        assertDoesNotThrow(()->{
            userRepository.save(user);
            testEntityManager.flush();
        });
    }

    @Test
    public void testFindByIdMethod(){
        Timestamp time= new Timestamp(System.currentTimeMillis());
        User user = new User("Abc", "cde", time, time);
        user.setUsername("abc@example.com");
        user.setPassword("Test@1234");
    User savedUser = userRepository.save(user);
        assertAll("test if data is saved",
            ()->assertNotEquals(savedUser.getId(), null),
            ()->assertTrue(savedUser.getFirstName().equals("Abc")),
            ()->assertTrue(savedUser.getLastName().equals("cde")),
            ()->assertTrue(savedUser.getUsername().equals("abc@example.com")),
            ()->assertTrue(savedUser.getPassword().equals("Test@1234")),
            ()->assertTrue(savedUser.getAccountCreated().equals(time)),
            ()->assertNull(savedUser.getQuestions()),
            ()->assertNull(savedUser.getAnswers())
        );
        Optional<User> optionalUser = userRepository.findById(savedUser.getId());
        assertAll("test findById method",
            ()->assertTrue(optionalUser.isPresent()),
            ()->assertNotNull(optionalUser.get())
        );
        User retrievedUser = optionalUser.get();
        assertAll("verify the result",
            ()->assertTrue(retrievedUser.getFirstName().equals("Abc")),
            ()->assertEquals(retrievedUser.getId(), savedUser.getId()),
            ()->assertNull(retrievedUser.getQuestions()),
            ()->assertNull(retrievedUser.getAnswers())        
        );
    }

    @Test
    public void testFindByUsernameAndPassword(){
        Timestamp time= new Timestamp(System.currentTimeMillis());
        User user = new User("Abc", "cde", time, time);
        user.setUsername("abc@example.com");
        user.setPassword("Test@1234");
        userRepository.save(user);
        Optional<UserView> optionalUser = userRepository.findByUsernameAndPassword("abc@example.com", "Test@1234",UserView.class);
        assertAll("test findByUsernameAndPassword method",
            ()->assertTrue(optionalUser.isPresent()),
            ()->assertNotNull(optionalUser.get())
        );
        UserView user1 = optionalUser.get();
        assertAll("verify the result",
            ()->assertTrue(user1.getFirstName().equals("Abc")),
            ()->assertEquals(user1.getId(), user.getId()),
            ()->assertNull(user1.getQuestions()),
            ()->assertNull(user1.getAnswers())        
        );
        Optional<UserView> optionalUser2 = userRepository.findByUsernameAndPassword("acd@test.com", "Test@1234",UserView.class);
        assertAll("test findByUsernameAndPassword method for non-existent user",
        ()->assertFalse(optionalUser2.isPresent()),
        ()->assertThrows(NoSuchElementException.class,()->optionalUser2.get())
    );
    }   
}
