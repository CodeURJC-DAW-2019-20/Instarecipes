package com.proyect.instarecipes;

import javax.annotation.PostConstruct;

import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDatabase {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private RecipesRepository recipesRepository;

    @PostConstruct
    private void initDatabase() {
        userRepository.save(new User("user1", "pepe@grillo.com", "pass", "Pepe", "Grillo", "sida", null, null, "ROLE_USER"));
        userRepository.save(new User("user2", "manu@gmail.com", "pass", "Manuel", "Savater", "awp's", null, null, "ROLE_USER"));
        userRepository.save(new User("user3", "trevodrap@hotmail.com", "pass", "Trevod", "Rap", "Toyacos", null, null, "ROLE_USER"));
        userRepository.save(new User("admin", "hola@adios.com", "adminpass", "Hamsa", "Jefe", "cerdo", null, null, "ROLE_USER", "ROLE_ADMIN"));

        recipesRepository.save(new Recipe("@boss99", null, "??? ", "¿¿¿¿", "Homemade Pizza!", "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!", 
            "ejemplo descripcion2", "15 min.", null, "Hard"));
        recipesRepository.save(new Recipe("@lady44", null, "???", "¿¿¿¿", "Avocado Salad", "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese, and an avocado…toss it alltogether, and done. It’s summery, healthy, and so good!", 
            "ejemplo descripcion2", "15 min.",null, "Hard"));
    }

}