package com.example.tasks.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class task {
   @Id
   @GeneratedValue
   private int id;
   private int userId;
   private String title;
   private String description;
   private String category ;
   private LocalDate dueDate;
   private boolean completed;
}
