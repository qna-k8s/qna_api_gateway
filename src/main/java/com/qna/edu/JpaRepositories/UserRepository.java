package com.qna.edu.JpaRepositories;

import java.util.Optional;

import com.qna.edu.Entities.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    public Optional<User> findByUsernameAndPassword(String username,String password);

    public <T> Optional<T> findByUsernameAndPassword(String username, String password, Class<T> type);

    public <T> Optional<T> findById(Long id, Class<T> type);

}
