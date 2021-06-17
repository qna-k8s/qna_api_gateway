package com.qna.edu.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.qna.edu.ValidationGroups.OnCreate;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(groups = {OnCreate.class})
    private String firstName;
    @NotBlank(groups = {OnCreate.class})
    private String lastName;
    private Timestamp accountCreated;
    private Timestamp accountUpdated;
    @NotBlank(groups = {OnCreate.class})
    @Column(unique = true)
    @Pattern(regexp = "^[^\\s@]+@([^\\s@.,]+\\.)+[^\\s@.,]{2,}$", message = "Invalid email address")
    private String username;
    @NotBlank(groups = {OnCreate.class})
    @Pattern(regexp = "^(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{9,}$",message = "Password should at least be 9 chars")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Question> questions;
    @OneToMany(mappedBy = "user")
    private List<Answer> answers;
    public User() {
    }
    public User(Long id, String firstName, String lastName, Timestamp accountCreated, Timestamp accountUpdated,
            List<Question> questions, List<Answer> answers, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountCreated = accountCreated;
        this.accountUpdated = accountUpdated;
        this.questions = questions;
        this.answers = answers;
        this.username = username;
        this.password = password;
    }
    public User(String firstName, String lastName, Timestamp accountCreated, Timestamp accountUpdated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountCreated = accountCreated;
        this.accountUpdated = accountUpdated;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Timestamp getAccountCreated() {
        return accountCreated;
    }
    public void setAccountCreated(Timestamp accountCreated) {
        this.accountCreated = accountCreated;
    }
    public Timestamp getAccountUpdated() {
        return accountUpdated;
    }
    public void setAccountUpdated(Timestamp accountUpdated) {
        this.accountUpdated = accountUpdated;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public List<Answer> getAnswers() {
        return answers;
    }
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
    
    
}
