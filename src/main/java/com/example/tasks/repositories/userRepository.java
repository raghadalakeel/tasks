package com.example.tasks.repositories;

import com.example.tasks.models.user;
import org.springframework.data.repository.CrudRepository;

public interface userRepository extends CrudRepository<user,Integer > {
    user findById(int userId);
    boolean existsByUserName(String name);
    user findByUserName(String name);

}
