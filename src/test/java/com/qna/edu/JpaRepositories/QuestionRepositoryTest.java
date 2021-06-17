package com.qna.edu.JpaRepositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.persistence.FlushModeType;

import com.qna.edu.Entities.Question;
import com.qna.edu.Entities.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class QuestionRepositoryTest {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    public void setUpDb(){
        // Create user
        Timestamp time= new Timestamp(System.currentTimeMillis());
        assertEquals(testEntityManager.getEntityManager().getFlushMode(), FlushModeType.AUTO);
        User user = new User("John", "cde", time, time);
        user.setUsername("john@example.com");
        user.setPassword("Test@1234");
        userRepository.save(user);
        // Create question
        Question question = new Question("setup question for testing", time, time);
        questionRepository.save(question);
        // Flush the changes to database
        testEntityManager.flush();
        // Clear the persistence context to avoid getting the data from the cache instead of database
        testEntityManager.clear();  
    }

    @Test
    public void validateQuestion(){

    }


    @Test
    // @Transactional
    public void testQuestionAndUserDependency(){
        // Create user
        Timestamp time= new Timestamp(System.currentTimeMillis());
        assertEquals(testEntityManager.getEntityManager().getFlushMode(), FlushModeType.AUTO);
        User user = new User("Abc", "cde", time, time);
        user.setUsername("abc@example.com");
        user.setPassword("Test@1234");
        userRepository.save(user);
        // Create question
        Question question = new Question("Testing question", time, time);
        questionRepository.save(question);
        // Flush the changes to database
        testEntityManager.flush();
        // Clear the persistence context to avoid getting the data from the cache instead of database
        testEntityManager.clear();
        // Retrive data from database
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword("abc@example.com", "Test@1234");
        Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
        assertAll("Verify that question list is not null",
            ()->{
                assertNotNull(optionalUser.get().getQuestions());
            },()->{
                assertEquals(optionalUser.get().getQuestions().size(), 0);
            });
        assertAll("Verify that answer list is not null",
            ()->{
                assertNotNull(optionalQuestion.get().getAnswers());
            },()->{
                assertEquals(optionalQuestion.get().getAnswers().size(), 0);
            });
    }

    @Test
    public void testAttachQuestionToUser(){
        // Retrieve the user and question created in beforeEach hook
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword("john@example.com", "Test@1234");
        List<Question> questionsList = questionRepository.findByQuestionTextStartingWith("setup question",Question.class);
        assertTrue(optionalUser.isPresent());
        assertEquals(questionsList.size(), 1);
        User user = optionalUser.get();
        Question question = questionsList.get(0);
        // Question: owner-side
        // Attach question to user
        user.getQuestions().add(question);
        // Save the user
        assertDoesNotThrow(()->{
            // Save is not calling sql update
            userRepository.save(user);
            testEntityManager.flush();
        });
        // so the size of the questions list is still zero
        assertEquals(user.getQuestions().size(), 0);
        // Likewise question is also not updated
        assertNull(question.getUser());
        // Checking after clearing the persistence context
        testEntityManager.clear();
        optionalUser = userRepository.findByUsernameAndPassword("john@example.com", "Test@1234");
        // Still zero
        assertEquals(optionalUser.get().getQuestions().size(), 0);
    }
    @Test
    public void testAttachUserToQuestion(){
        // Retrieve the user and question created in beforeEach hook
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword("john@example.com", "Test@1234");
        List<Question> questionsList = questionRepository.findByQuestionTextStartingWith("setup question",Question.class);
        assertTrue(optionalUser.isPresent());
        assertEquals(questionsList.size(), 1);
        User user = optionalUser.get();
        Question question = questionsList.get(0);
        // Question: owner-side
        // Attach the user to question
        question.setUser(user);
        // Save the user
        assertDoesNotThrow(()->{
            // SQL update is being triggered since the question has foriegn key and it is going to get update
            questionRepository.save(question);
            testEntityManager.flush();
        });
        // Since database is being updated with foriegn key, we get correct results
        assertEquals(user.getQuestions().size(), 1);
        assertNotNull(question.getUser());
    }

    @Test
    public void searchByQuestionText(){
        List<Question> questionsList = questionRepository.findByQuestionTextStartingWith("setup question",Question.class);
        assertTrue(questionsList.size()>0);
        List<Question> questionsList2 = questionRepository.findByQuestionTextStartingWith("Test",Question.class);
        assertFalse(questionsList2.size()>0);

    }
}
