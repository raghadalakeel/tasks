package com.example.tasks.controllers;

import com.example.tasks.models.user;
import com.example.tasks.services.userService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class userController {
    private userService service;
    public userController(userService service){
        this.service=service;
    }
    @GetMapping(value = "/userInfo" )
    public user getUser(@RequestParam int id) {
        user User = service.finduserBbyId(id);
        return  User;
    }
}
