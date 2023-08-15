package com.example.tasks.controllers;

import com.example.tasks.models.user;
import com.example.tasks.services.userService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class userController {
    private userService service;
    public userController(userService service){
        this.service=service;
    }

    ///user profile Feature
    @GetMapping( "/profile" )
    public ResponseEntity<user> getUser( Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int userId = service.getUserIdByUsername(userDetails.getUsername());
        user User = service.finduserBbyId(userId);
        if (User != null) {
            return ResponseEntity.ok(User);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    //registration feature
    //show registartion page
//    @GetMapping("/registerForm")
//    public ModelAndView showRegistrationForm(Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("user",new user());
//        modelAndView.setViewName("register.html");
////        model.addAttribute("user", new user());
//        return modelAndView;
//    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        user userr = new user();
        model.addAttribute("user", userr);
        return "register";
    }
//    @PostMapping("/register")
//    public ResponseEntity<String> RegisterUser(@RequestBody user userr){
//        boolean added = service.addUser(userr);
//        if(added)
//        {return ResponseEntity.ok("user have been added successfully");
//        }else return ResponseEntity.ok("user have not added ");
//    }

    @PostMapping("/register/save")
    public String registration( @ModelAttribute("user") user userr,
                               BindingResult result,
                               Model model){
        user existingUser =service.findByUserName(userr.getUserName());

        if(existingUser != null && existingUser.getUserName() != null && !existingUser.getUserName().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userr);
            return "/register";
        }

        service.addUser(userr);
        return "redirect:/register?success";
    }


    //login@GetMapping("/login")
//    public ModelAndView showLoginForm() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login.html");
//        return modelAndView; // Return the name of the HTML template
//    }
  @GetMapping("/login")
    public void login(){

    }

    @GetMapping("/loginsave")
    public void loginProcess(){

    }


    //logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login"; // return url
    }

}
