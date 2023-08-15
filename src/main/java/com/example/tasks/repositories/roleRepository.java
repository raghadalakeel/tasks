package com.example.tasks.repositories;

import com.example.tasks.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface roleRepository extends CrudRepository<Role, Long> {


    Role findByName(String name);
}