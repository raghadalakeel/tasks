package com.example.tasks.repositories;

import com.example.tasks.models.task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface taskRepository extends CrudRepository<task,Integer> {
    List<task> findAllByUserId(int userId);
    task findByTitle(String name);
    task findById(int id);
    task findByIdAndUserId(int id, int UserId);

    List<task> findAllByTitleAndUserId(String name,int userId);
    List<task> findAllByCategoryAndUserId(String category,int userId);
}
