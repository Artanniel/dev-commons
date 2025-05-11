package com.interall.dev_commons.project1.controller;

import com.interall.dev_commons.project1.model.User;
import com.interall.dev_commons.project1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listarUsuarios() {
        return userService.listarUsuarios();
    }

    @PostMapping
    public User criarUsuario(@RequestBody User user) {
        return userService.salvarUsuario(user);
    }
}