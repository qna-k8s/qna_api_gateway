package com.qna.edu.Entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.qna.edu.ValidationGroups.OnCreate;




@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(groups = {OnCreate.class})
    private String questionText;
    private Timestamp createdTimestamp;
    private Timestamp updatedTimestamp;
    @OneToMany(mappedBy = "question",orphanRemoval = true)
    private List<Answer> answers;
    @ManyToOne
    private User user;
    @ManyToMany
    private Set<Category> categories;
    public Question() {
    }
    
    public Question(Long id, String questionText, Timestamp createdTimestamp, Timestamp updatedTimestamp, List<Answer> answers,
            User user, Set<Category> categories) {
        this.id = id;
        this.questionText = questionText;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
        this.answers = answers;
        this.user = user;
        this.categories = categories;
    }
    public Question(String questionText, Timestamp createdTimestamp, Timestamp updatedTimestamp) {
        this.questionText = questionText;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    
}
