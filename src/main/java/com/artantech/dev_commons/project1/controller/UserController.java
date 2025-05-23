package com.artantech.dev_commons.project1.controller;

import com.artantech.dev_commons.project1.entity.User;
import com.artantech.dev_commons.project1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @PostMapping
    public User createUsers(@RequestBody User user) {
        return userService.saveUser(user);
    }
}