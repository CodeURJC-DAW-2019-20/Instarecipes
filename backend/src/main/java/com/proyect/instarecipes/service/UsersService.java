package com.proyect.instarecipes.service;

import java.util.Optional;

import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Optional<User> getActualUser(Long id) {
        return usersRepository.findById(id);
    }
    
}