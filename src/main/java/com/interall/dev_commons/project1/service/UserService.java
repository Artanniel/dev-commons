package com.interall.dev_commons.project1.service;

import com.interall.dev_commons.project1.model.User;
import com.interall.dev_commons.project1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    public User salvarUsuario(User user) {
        return userRepository.save(user);
    }
}
