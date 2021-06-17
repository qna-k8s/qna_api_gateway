package com.qna.edu.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qna.edu.Entities.Category;
import com.qna.edu.Entities.Question;
import com.qna.edu.Entities.User;
import com.qna.edu.JpaRepositories.CategoryRepository;
import com.qna.edu.JpaRepositories.QuestionRepository;
import com.qna.edu.JpaRepositories.UserRepository;
import com.qna.edu.Projections.QuestionView;
import com.qna.edu.Responses.QuestionCreatedResponse;
import com.qna.edu.ValidationGroups.OnCreate;
import com.qna.edu.ValidationGroups.OnUpdate;
import com.qna.edu.util.Authentication;
import com.qna.edu.util.InvalidRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping(value = "/v1/question")
public class QuestionController {
    
    @Autowired
    private ApplicationContext context;
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Authentication authentication;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method handles requests to create new question
     * @param question
     * @param authHeader
     * @return
     */

    @PostMapping(value="")
    public ResponseEntity<Object> createQuestion(@Validated(OnCreate.class) @RequestBody Question question,
        @RequestHeader(name = "authorization") String authHeader) {
        User user =  authentication.authenticate(authHeader,User.class);
        // Check if the categories exist in db and associate them to question
        Set<Category> questionCategories = new HashSet<>();
        for(Category category: question.getCategories()){
            Optional<Category> optionalCategory = categoryRepository.findByCategory(category.getCategory());
            if(optionalCategory.isEmpty()){
                questionCategories.add(categoryRepository.save(category));
            }
            else{
                questionCategories.add(optionalCategory.get());
            }
        }
        question.setCategories(questionCategories);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        question.setCreatedTimestamp(time);
        question.setUpdatedTimestamp(time);
        question.setUser(user);
        questionRepository.save(question);
        user.getQuestions().add(question);
        userRepository.save(user);
        QuestionCreatedResponse response = context.getBean(QuestionCreatedResponse.class);
        response.setId(question.getId());
        response.setCategories(question.getCategories());
        response.setCreated_timestamp(question.getCreatedTimestamp());
        response.setUpdated_timestamp(question.getUpdatedTimestamp());
        response.setQuestion_text(question.getQuestionText());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id, 
        @RequestHeader(name = "authorization") String authHeader){
        
        User user = authentication.authenticate(authHeader, User.class);
        Optional<Question> optionalQuestion = questionRepository.findByIdAndUser(id, user,Question.class);
        if(optionalQuestion.isEmpty()){
            InvalidRequestException ex = context.getBean(InvalidRequestException.class,"Question not found!!");
            throw ex;
        }
        questionRepository.delete(optionalQuestion.get());
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getQuestionById(@PathVariable Long id){
        Optional<QuestionView> questionView = questionRepository.findById(id, QuestionView.class);
        if(questionView.isEmpty()){
            InvalidRequestException ex = context.getBean(InvalidRequestException.class,"Question not found!!");
            throw ex;
        }
        return ResponseEntity.status(HttpStatus.OK).body(questionView.get());
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateQuestion(@PathVariable Long id,@Validated(OnUpdate.class) @RequestBody Question question,
    @RequestHeader(name = "authorization") String authHeader){
        User user = authentication.authenticate(authHeader, User.class);
        Optional<Question> optionalQuestion = questionRepository.findByIdAndUser(id, user,Question.class);
        if(optionalQuestion.isEmpty()){
            InvalidRequestException ex = context.getBean(InvalidRequestException.class,"Question not found!!");
            throw ex;
        }
        Question dbQuestion = optionalQuestion.get();
        Set<Category> dbCategories = dbQuestion.getCategories();
        for(Category category: question.getCategories()){
            if(dbCategories.contains(category)){
                continue;
            }
            Optional<Category> optionalCategory = categoryRepository.findByCategory(category.getCategory());
            if(optionalCategory.isEmpty()){
                dbCategories.add(categoryRepository.save(category));
            }
            else{
                dbCategories.add(optionalCategory.get());
            }
        }
        if(question.getQuestionText()!=null){
            dbQuestion.setQuestionText(question.getQuestionText());
        }
        dbQuestion.setCategories(dbCategories);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        dbQuestion.setUpdatedTimestamp(time);
        questionRepository.save(dbQuestion);
        Optional<QuestionView> response = questionRepository.findById(dbQuestion.getId(), QuestionView.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllQuestionsWithSorting(@RequestParam(defaultValue = "questionText") String sort,
    @RequestParam(defaultValue = "0") int page){
        if(sort.equals("mostRecent")){
            sort="createdTimestamp";
            Page<QuestionView> questions = questionRepository.findBy(PageRequest.of(page, 5,Sort.by(sort).descending()),QuestionView.class);
            return ResponseEntity.status(HttpStatus.OK).body(questions);
        }
        Page<QuestionView> questions = questionRepository.findBy(PageRequest.of(page, 5,Sort.by(sort)),QuestionView.class);
        return ResponseEntity.status(HttpStatus.OK).body(questions); 
    }
}
