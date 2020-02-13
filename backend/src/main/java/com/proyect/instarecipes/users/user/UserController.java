package com.proyect.instarecipes.users.user;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.proyect.instarecipes.users.UsersRepository;
import com.proyect.instarecipes.IndexController;

import org.hibernate.HibernateException;
import org.springframework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    

    @Autowired
    private UsersRepository uRepository;

    @PostConstruct
    public void init(){
        long id=0;
        String username="chiquito";
        String email="asdasdas@asddas.com";
        String password="12345";
        String[] role=null;			//If he's an administrator
        String name="pepe";
        String surname="surmano";
        String allergens="null";
        Set<User> followers = new HashSet<User>();
        Set<User> following = new HashSet<User>();
        uRepository.save(new User(id,
                                username,
                                email,
                                password,
                                role,
                                name,
                                surname,
                                allergens,
                                followers,
                                following)
                        );
    }

    IndexController indexController = new IndexController();
    User u = new User();

    @PostMapping("/signUp")
    public String loginPage(Model model, User user){
        try{
            u = user;
            return "signUp";
        }catch(HibernateException cve){
            cve.printStackTrace();
            System.out.println("User already exists !!");
            return "login";
        }
    }
    @PostMapping("/index")
    public String registerUser(Model model, User user){
        u.setName(user.getName());
        if(user.getSurname()!=null){
            u.setSurname(user.getSurname());
        }else{
            u.setSurname("null");
        }
        if(user.getAllergens()!="null"){
            u.setAllergens(user.getAllergens());
        }else{
            u.setAllergens("null");
        }
        u.setRole(null);
        uRepository.save(u);

        model.addAttribute("recipes", indexController.getRecipes());

        return "index";
    }
}