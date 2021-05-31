package com.avanade.security.app.controller;

import com.avanade.security.app.model.UserModel;
import com.avanade.security.app.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list-all")
    public Iterable<UserModel> listAll(){
        return userRepository.findAll();
    }

    @PostMapping("/save")
    public void save(@RequestBody UserModel user){
        userRepository.save(user);
    }
    
}
