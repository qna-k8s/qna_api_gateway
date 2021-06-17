package com.qna.edu.JpaRepositories;

import java.util.Optional;

import com.qna.edu.Entities.Answer;
import com.qna.edu.Entities.Question;
import com.qna.edu.Entities.User;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer,Long> {
    <T> Optional<T> findById(Long id, Class<T> type);
    Optional<Answer> findByIdAndQuestionOrderByCreatedTimestamp(Long id,Question question);
    <T> Optional<T> findByIdAndUser(Long id, User user, Class<T> type);
    void deleteByIdAndUser(Long id, User user);

}
