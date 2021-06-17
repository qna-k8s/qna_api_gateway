package com.qna.edu.Controllers;

import java.sql.Timestamp;
import java.util.Optional;

import javax.validation.Valid;

import com.qna.edu.Entities.Answer;
import com.qna.edu.Entities.Question;
import com.qna.edu.Entities.User;
import com.qna.edu.JpaRepositories.AnswerRepository;
import com.qna.edu.JpaRepositories.QuestionRepository;
import com.qna.edu.Projections.AnswerView;
import com.qna.edu.Projections.QuestionView;
import com.qna.edu.util.Authentication;
import com.qna.edu.util.InvalidRequestException;
import com.qna.edu.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/question/{question_id}/answer")
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private Authentication authentication;

    @Autowired 
    private ApplicationContext context;

    @PostMapping(value = "")
    public ResponseEntity<Object> postAnswer(@Valid @RequestBody Answer answer,
    @RequestHeader(name = "authorization") String authHeader,@PathVariable Long question_id){
        User user = authentication.authenticate(authHeader, User.class);
        Optional<Question> optionalQuestion = questionRepository.findById(question_id);
        if(optionalQuestion.isEmpty()){
            InvalidRequestException ex= context.getBean(InvalidRequestException.class,"Question not found");
            throw ex;
        }
        Timestamp time= new Timestamp(System.currentTimeMillis());
        answer.setCreatedTimestamp(time);
        answer.setUpdatedTimestamp(time);
        answer.setUser(user);
        answer.setQuestion(optionalQuestion.get());
        answerRepository.save(answer);
        Optional<AnswerView> optionalAnswer = answerRepository.findById(answer.getId(), AnswerView.class);
        return ResponseEntity.status(HttpStatus.OK).body(optionalAnswer.get());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAnswerbyId(@PathVariable Long id){
        Optional<AnswerView> optionalAnswer = answerRepository.findById(id, AnswerView.class);
        if(optionalAnswer.isEmpty()){
            NotFoundException exception = context.getBean(NotFoundException.class,"Answer Not found!!");
            throw exception;
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalAnswer.get());
    }
    @GetMapping(value = "")
    public ResponseEntity<Object> getAnswersForQuestion(@PathVariable Long question_id){
        Optional<QuestionView> optionalQuestion = questionRepository.findById(question_id,QuestionView.class);
        if(optionalQuestion.isEmpty()){
            NotFoundException exception = context.getBean(NotFoundException.class,"Answer Not found!!");
            throw exception;
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalQuestion.get().getAnswers());
    }
    @PutMapping(value = "/{answer_id}")
    public ResponseEntity<Object> updateAnswer(@PathVariable Long answer_id,@Valid @RequestBody Answer answer,
    @RequestHeader(name = "authorization") String authHeader){
        User user  = authentication.authenticate(authHeader, User.class);
        Optional<Answer> optionalAnswer= answerRepository.findByIdAndUser(answer_id,user,Answer.class);
        if(optionalAnswer.isEmpty()){
            NotFoundException exception = context.getBean(NotFoundException.class,"Answer not found!!");
            throw exception;
        }
        Answer dbAnswer = optionalAnswer.get();
        dbAnswer.setAnswerText(answer.getAnswerText());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        dbAnswer.setUpdatedTimestamp(time);
        answerRepository.save(dbAnswer);
        Optional<AnswerView> response = answerRepository.findById(dbAnswer.getId(), AnswerView.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping(value = "/{answer_id}")
    public ResponseEntity<Object> deleteAnswer(@PathVariable Long answer_id,@RequestHeader(name = "authorization") String authHeader){
        User user = authentication.authenticate(authHeader, User.class);
        Optional<Answer> answer = answerRepository.findByIdAndUser(answer_id, user, Answer.class);
        if(answer.isEmpty()){
            NotFoundException exception = context.getBean(NotFoundException.class,"Answer Not found!!");
            throw exception;
        }
        answerRepository.delete(answer.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
