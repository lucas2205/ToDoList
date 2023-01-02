package com.todolist.ensolvers.repository;

import com.todolist.ensolvers.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Category findByName(String name);

    List<Category> findByUser_Id(Long id);
}
