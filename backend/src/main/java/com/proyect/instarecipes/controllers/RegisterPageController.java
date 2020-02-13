package com.proyect.instarecipes.controllers;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Set;
// import java.util.HashSet;

// import javax.annotation.PostConstruct;

// import com.proyect.instarecipes.models.Recipe;
// import com.proyect.instarecipes.models.User;
// import com.proyect.instarecipes.repositories.UsersRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.ui.Model;

// @Controller
// public class RegisterPageController {

//     @Autowired
//     private UsersRepository usersRepository;

//     @PostConstruct
// 	public void init() {
//         long id=0;
//         String username="chiquito";
//         String email="asdasdas@asddas.com";
//         String password="12345";
//         String[] role=null;			//If he's an administrator
//         String name="pepe";
//         String surname="surmano";
//         String allergens="null";
//         Set<User> followers = new HashSet<User>();
//         Set<User> following = new HashSet<User>();
//         usersRepository.save(new User(id,
//                                 username,
//                                 email,
//                                 password,
//                                 role,
//                                 name,
//                                 surname,
//                                 allergens,
//                                 followers,
//                                 following)
//                         );
//     }

//     @PostMapping("/")
//     public String postRecipe(Model model, User user) {
//         User u = new User();
//         //List<String> role = new ;
//         model.addAttribute("user", user);
//         //role.add("admin");
//         //u.setRole(role);
//         u=user;
//         usersRepository.save(u);
//         return "index"; 
//     }
    
// }

public class RegisterPageController {
    

}