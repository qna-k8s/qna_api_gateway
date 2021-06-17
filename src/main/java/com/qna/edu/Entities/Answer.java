package com.qna.edu.Entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String answerText;
    private Timestamp createdTimestamp;
    private Timestamp updatedTimestamp;
    @ManyToOne
    private User user;
    @ManyToOne
    private Question question;
    public Answer() {
    }
    
    public Answer(Long id, String answerText, Timestamp createdTimestamp, Timestamp updatedTimestamp, User user, Question question) {
        this.id = id;
        this.answerText = answerText;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
        this.user = user;
        this.question = question;
    }

    public Answer(String answerText, Timestamp createdTimestamp, Timestamp updatedTimestamp) {
        this.answerText = answerText;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAnswerText() {
        return answerText;
    }
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public Timestamp getUpdatedTimestamp() {
        return updatedTimestamp;
    }
    public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
}
