package com.example.tasks.controllers;

import com.example.tasks.models.task;
import com.example.tasks.models.user;
import com.example.tasks.services.taskService;
import com.example.tasks.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Controller
public class taskController {
    @Autowired
    private taskService service;
   @Autowired
    private userService Uservice;
//display the user tasks page
//working
@GetMapping("/tasks")
public String getTasks(Authentication authentication, Model model) {

    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        int userId = Uservice.getUserIdByUsername(userDetails.getUsername());


        model.addAttribute("userId", userId);
        List<task> tasks = service.getTasks(userId);
        if (tasks.isEmpty()) {
            return "noTasks";
        } else {
            model.addAttribute("tasks", tasks);
            return "tasks";
        }
    }

 return"index";
}

    @GetMapping("/get-task/{taskId}")
    @ResponseBody
    public ResponseEntity<task> getTaskDetails(@PathVariable int taskId) {

        task taskk = service.getTaskById(taskId);
        if (taskk != null) {
            return ResponseEntity.ok(taskk);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//button
//add a new task
//working
@PostMapping("/add")
public String addTask(@ModelAttribute("task") task taskk, Model model, Authentication authentication) {
    if (authentication != null) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Authenticated User: " + userDetails.getUsername());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        System.out.println("Authorities: " + authorities);
    } else {
        System.out.println("Not Authenticated");
    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    int userId = Uservice.getUserIdByUsername(userDetails.getUsername());


    taskk.setUserId(userId);
    task Task = service.addnewTask(taskk);
    model.addAttribute("task", Task);
    List<task> tasks = service.getTasks(userId); // Get updated task list
    model.addAttribute("tasks", tasks);
    return "redirect:/tasks?success";
}
//working
@PostMapping("/completed")
public ResponseEntity<String> markTaskCompleted(@RequestParam int id, @RequestParam boolean completed) {
    System.out.println("iam in set completed controller");
    service.setCompleted(id,completed);
    return ResponseEntity.ok("Task completion status updated");
}

    // edit button
    //update a task
    @PostMapping("/Update")
    public String UpdateTAsk(@RequestParam int id,@ModelAttribute("task") task taskk, Model model, Authentication authentication) {
//        if (authentication != null) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            System.out.println("Authenticated User: " + userDetails.getUsername());
//
//            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//            System.out.println("Authorities: " + authorities);
//        } else {
//            System.out.println("Not Authenticated");
//        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int userId = Uservice.getUserIdByUsername(userDetails.getUsername());
        taskk.setUserId(userId);
        taskk.setId(id);
        task Task = service.Update(taskk);
        model.addAttribute("task", Task);
        List<task> tasks = service.getTasks(userId); // Get updated task list
        model.addAttribute("tasks", tasks);
        return "tasks";
    }



    // delete button
    //delete a task
    // check the request param form thymleaf template
    //working
    @PostMapping ("/delete")
    public String deleteTask( @RequestParam int id,Model model,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int userId = Uservice.getUserIdByUsername(userDetails.getUsername());

        boolean removed = service.deleteTask( id);

        model.addAttribute("userId", userId);
        List<task> tasks = service.getTasks(userId); // Get updated task list
        model.addAttribute("tasks", tasks);

        if (removed) {
            return "redirect:/tasks?successdelete";
        } else {
            System.out.println("not deleted");
            return "redirect:/tasks?failuredelete";
        }
    }




    //assign task for a specific category
    @PostMapping("/category")
    public String assignCategory(@RequestParam int id, @RequestParam String category) {
        service.assignCategory(category, id);

        return "tasks"; // Redirect to the task list page
    }


     // search field
    //search for a task
     //working
     @GetMapping("/search")
     public String searchTasks(@RequestParam String name, Model model,Authentication authentication) {
         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         int userId = Uservice.getUserIdByUsername(userDetails.getUsername());
         List<task> searchedTasks = service.SearchTask(name, userId);
         model.addAttribute("tasks", searchedTasks);
         return "redirect:/tasks";
     }




    // category filter
    //display a tasks related to each category
    @GetMapping("/categorizied")
    public String getTaskByCategory(@RequestParam String category,Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int userId = Uservice.getUserIdByUsername(userDetails.getUsername());
        List<task> searchedTasks = service.getTaskBycategory(category,userId);
        model.addAttribute("tasks", searchedTasks);
        return "redirect:/tasks";
    }



}




