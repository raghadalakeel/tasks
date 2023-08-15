package com.example.tasks.services;

import com.example.tasks.models.Role;
import com.example.tasks.models.user;
import com.example.tasks.repositories.roleRepository;
import com.example.tasks.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class userService  {
  @Autowired
  private userRepository userRepo;
  @Autowired
  private roleRepository roleRepo;

  private PasswordEncoder passwordEncoder;
  @Autowired
    public userService(userRepository userRepo, roleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
    this.roleRepo = roleRepo;
    this.passwordEncoder = passwordEncoder;
  }
    // view profile feature
    public user finduserBbyId(int userId){
      user returnedUser = userRepo.findById(userId);
      return returnedUser;
    }
  public int getUserIdByUsername(String userName){
    user returnedUser = userRepo.findByUserName(userName);
    return returnedUser.getId();
  }
  public user findByUserName(String userName){
    user returnedUser = userRepo.findByUserName(userName);
    return returnedUser;
  }
    // Registration feature
  public boolean addUser(user User){
    if (userRepo.existsByUserName(User.getUserName()))
      return false;
    else{
      // I have encrypted the password using spring security
      user newuser =new user();
      newuser.setUserName(User.getUserName());
      newuser.setPassword(passwordEncoder.encode(User.getPassword()));
      newuser.setFirstName(User.getFirstName());

      newuser.setLastName(User.getUserName());
      Role role = roleRepo.findByName("ROLE_USER");
      if(role == null){
        role = checkRoleExist();
      }
      newuser.setRoles(Arrays.asList(role));
      userRepo.save(newuser);
      return true;
    }


  }
  private Role checkRoleExist(){
    Role role = new Role();
    role.setName("ROLE_USER");
    return roleRepo.save(role);
  }
}
