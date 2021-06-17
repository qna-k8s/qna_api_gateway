package com.qna.edu.Projections;

import java.sql.Timestamp;
import java.util.List;

import com.qna.edu.Entities.Category;

public interface UserView {
    Long getId();
    String getUsername();
    String getFirstName();
    String getLastName();
    Timestamp getAccountCreated();
    Timestamp getAccountUpdated();
    List<UserQuestionView> getQuestions();
    List<UserAnswerView> getAnswers();
    interface UserQuestionView{
        Long getId();
        String getQuestionText();
        Timestamp getCreatedTimestamp();
        Timestamp getUpdatedTimestamp();
        List<Category> getCategories();
    }
    interface UserAnswerView{
        Long getId();
        String getAnswerText();
        Timestamp getCreatedTimestamp();
        Timestamp getUpdatedTimestamp();
        UserAnswerQuestionView getQuestion();
        interface UserAnswerQuestionView{
            Long getId();
            String getQuestionText();
        }
    }
}
