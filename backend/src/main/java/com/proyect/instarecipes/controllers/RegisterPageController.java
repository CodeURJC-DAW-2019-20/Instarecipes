package com.proyect.instarecipes.controllers;

// import com.proyect.instarecipes.models.User;
// import com.proyect.instarecipes.repositories.UsersRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;

// import java.util.Arrays;

// @Controller
// public class RegisterPageController{

//     @Autowired
//     UsersRepository users;

//     @PostMapping("/register")
//     public String register(User user) {
//         user.setRoles(Arrays.asList("ROLE_USER"));
//         user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//         users.save(user);
//         //model.addAttribute("cod","El usuario ha sido registrado");
//         return "created";
//     }
// }