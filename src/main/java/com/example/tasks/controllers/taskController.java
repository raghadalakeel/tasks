package com.example.tasks.controllers;

import com.example.tasks.models.task;
import com.example.tasks.services.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class taskController {
private taskService service;

@Autowired
    public taskController(taskService service) {
        this.service = service;
    }

@GetMapping("/userId")
    public ResponseEntity<List<task>> getTasks(@RequestParam int userId){
     List<task> tasks = service.getTasks(userId);
    if (tasks.isEmpty()) {
        // If no tasks are found, we return a 404 Not Found status
        return ResponseEntity.notFound().build();
    } else {
        // If movies are found, return them with a 200 OK status
        return ResponseEntity.ok(tasks);
    }

}
    @PostMapping("/add")
    //check how to send entity in request body
        public ResponseEntity<task> addTask(@RequestBody task taskk){
                 task Task = service.addnewTask(taskk);
            return ResponseEntity.ok(Task);
    }
    @PostMapping("/completed")
    public ResponseEntity<String>setCompleted(@RequestPart int id){
        service.setCompleted(id);
        return ResponseEntity.ok("task has been marked completed");
    }
    @PostMapping("/update")
    public ResponseEntity<task>UpdateTask(@RequestBody task taskk){
        task Task = service.Update(taskk);
        return ResponseEntity.ok(Task);
    }
    @DeleteMapping(value="/delete" )
    public ResponseEntity<String> deleteTask(@RequestParam int userId ,@RequestParam int id) {
        boolean removedFromFavorites = service.deleteTask(userId,id);
        String message;
        if (removedFromFavorites) {
            message = "The task has been deleted successfully!";
        } else {
            message = "we could not delete the task ";
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    @PostMapping("/category")
    public ResponseEntity<String>assignCategory(@RequestParam int id ,@RequestParam String category){
        service.assignCategory(category,id);
        return ResponseEntity.ok("task been assigned to this category :"+category);
    }
    @GetMapping("/search")
    public ResponseEntity<List<task>> searchMovies(@RequestParam String name,@RequestParam int userId) {
        List<task> searchedMovies = service.SearchTask(name,userId);
        return ResponseEntity.ok(searchedMovies);
    }
    @GetMapping("/search")
    public ResponseEntity<List<task>> getTaskByCategory(@RequestParam String category, @RequestParam int userId) {
        List<task> searchedMovies = service.SearchTask(category,userId);
        return ResponseEntity.ok(searchedMovies);
    }



}




