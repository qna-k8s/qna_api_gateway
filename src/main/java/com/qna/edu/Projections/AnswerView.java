package com.qna.edu.Projections;

import java.sql.Timestamp;

public interface AnswerView {
    Long getId();
    String getAnswerText();
    Timestamp getCreatedTimestamp();
    Timestamp getUpdatedTimestamp();
    AnswerUserView getUser();
    AnswerQuestionView getQuestion();
    interface AnswerUserView{
        String getUsername();
        String getFirstName();
        String getLastName();
    }
    interface AnswerQuestionView{
        Long getId();
        String getQuestionText();
        Timestamp getCreatedTimestamp();
        Timestamp getUpdatedTimestamp();
        AnswerQuestionUserView getUser();
        interface AnswerQuestionUserView{
            String getUsername();
            String getFirstName();
            String getLastName();
        }
    }
}
