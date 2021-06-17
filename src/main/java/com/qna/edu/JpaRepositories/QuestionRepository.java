package com.qna.edu.JpaRepositories;

import java.util.List;
import java.util.Optional;

import com.qna.edu.Entities.Question;
import com.qna.edu.Entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    <T> Optional<T> findById(Long id, Class<T> type);
    <T> List<T> findByQuestionTextStartingWith(String question_text,Class<T> type);
    <T> Optional<T> findByIdAndUser(Long id, User user,Class<T> type);
    // <T> Page<T> findAll(Pageable pageable,Class<T> type);
    <T> Page<T> findBy(Pageable pageable,Class<T> type);

}
