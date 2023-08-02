package com.example.tasks.services;

import com.example.tasks.models.user;
import com.example.tasks.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService  {
  private userRepository userRepo;

  @Autowired
    public userService(userRepository userRepo) {
        this.userRepo = userRepo;
    }
    // view profile feature
    public user finduserBbyId(int userId){
      user returnedUser = userRepo.findById(userId);
      return returnedUser;
    }

    // Registration feature
  public boolean addUser(user User){
    if (userRepo.existsByUserName(User.getUserName()))
      return false;
    else{
      user newuser =new user();
      newuser.setUserName(User.getUserName());
      newuser.setPassword(User.getPassword());
      newuser.setFirstName(User.getFirstName());
      newuser.setLastName(User.getUserName());
      userRepo.save(newuser);
      return true;
    }

  }
}
