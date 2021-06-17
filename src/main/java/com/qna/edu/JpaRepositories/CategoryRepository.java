package com.qna.edu.JpaRepositories;

import java.util.Optional;

import com.qna.edu.Entities.Category;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long>{
    Optional<Category> findByCategory(String category);
}
