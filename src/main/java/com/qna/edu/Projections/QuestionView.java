package com.qna.edu.Projections;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.qna.edu.Entities.Category;

public interface QuestionView {
    Long getId();
    String getQuestionText();
    Timestamp getCreatedTimestamp();
    Timestamp getUpdatedTimestamp();
    List<QuestionAnswerView> getAnswers();
    QuestionUserView getUser();
    Set<Category> getCategories();
    interface QuestionAnswerView{
        Long getId();
        String getAnswerText();
        Timestamp getCreatedTimestamp();
        Timestamp getUpdatedTimestamp();
        QuestionAnswerUserView getUser();
        interface QuestionAnswerUserView{
            String getUsername();
            String getFirstName();
            String getLastName();
        }
    }

    interface QuestionUserView{
        String getUsername();
        String getFirstName();
        String getLastName();
    }
}
