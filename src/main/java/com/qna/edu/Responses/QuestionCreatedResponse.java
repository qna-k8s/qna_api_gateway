package com.qna.edu.Responses;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.qna.edu.Entities.Answer;
import com.qna.edu.Entities.Category;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class QuestionCreatedResponse {
    private Long id;
    private String question_text;
    private Timestamp created_timestamp;
    private Timestamp updated_timestamp;
    private Set<Category> categories;
    private List<Answer> answers;
    public QuestionCreatedResponse() {
    }
    
    public QuestionCreatedResponse(Long id, String question_text, Timestamp created_timestamp,
            Timestamp updated_timestamp, Set<Category> categories, List<Answer> answers) {
        this.id = id;
        this.question_text = question_text;
        this.created_timestamp = created_timestamp;
        this.updated_timestamp = updated_timestamp;
        this.categories = categories;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuestion_text() {
        return question_text;
    }
    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }
    public Timestamp getCreated_timestamp() {
        return created_timestamp;
    }
    public void setCreated_timestamp(Timestamp created_timestamp) {
        this.created_timestamp = created_timestamp;
    }
    public Timestamp getUpdated_timestamp() {
        return updated_timestamp;
    }
    public void setUpdated_timestamp(Timestamp updated_timestamp) {
        this.updated_timestamp = updated_timestamp;
    }
    public Set<Category> getCategories() {
        return categories;
    }
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
    
}
