package com.proyect.instarecipes;

import javax.annotation.PostConstruct;

import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDatabase {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;

    @PostConstruct
    private void initDatabase() {
        //Users examples
        userRepository.save(new User("user1", "pepe@grillo.com", "pass", "Pepe", "Grillo", "sida", null, null, "ROLE_USER"));
        userRepository.save(new User("user2", "manu@gmail.com", "pass", "Manuel", "Savater", "awp's", null, null, "ROLE_USER"));
        userRepository.save(new User("user3", "trevodrap@hotmail.com", "pass", "Trevod", "Rap", "Toyacos", null, null, "ROLE_USER"));
        userRepository.save(new User("admin", "hola@adios.com", "adminpass", "Hamsa", "Jefe", "cerdo", null, null, "ROLE_USER", "ROLE_ADMIN"));

        //Recipes examples
        recipesRepository.save(new Recipe("@boss99", null, "??? ", "¿¿¿¿", "Homemade Pizza!", "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!", 
            "ejemplo descripcion2", "15 min.", null, "Hard"));
        recipesRepository.save(new Recipe("@lady44", null, "???", "¿¿¿¿", "Avocado Salad", "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese, and an avocado…toss it alltogether, and done. It’s summery, healthy, and so good!", 
            "ejemplo descripcion2", "15 min.",null, "Hard"));

        //Ingredients examples
        ingredientsRepository.save(new Ingredient("Potatoes"));
        ingredientsRepository.save(new Ingredient("Honey"));
        ingredientsRepository.save(new Ingredient("Fish"));
        ingredientsRepository.save(new Ingredient("Tomatoes"));
        ingredientsRepository.save(new Ingredient("Milk"));
        ingredientsRepository.save(new Ingredient("Bread"));

        //Categories examples
        categoriesRepository.save(new Category("Desserts"));
        categoriesRepository.save(new Category("Starters"));
        categoriesRepository.save(new Category("Main"));
        categoriesRepository.save(new Category("Soups"));
        categoriesRepository.save(new Category("Salad"));
        categoriesRepository.save(new Category("Burgers"));
        categoriesRepository.save(new Category("Pizzas"));
        categoriesRepository.save(new Category("Smoothies"));
        categoriesRepository.save(new Category("BreakFast"));
        categoriesRepository.save(new Category("Brunch"));
        categoriesRepository.save(new Category("Dinner"));

        //Cooking Styles examples
        cookingStylesRepository.save(new CookingStyle("Vegan"));
        cookingStylesRepository.save(new CookingStyle("Vegetarian"));
        cookingStylesRepository.save(new CookingStyle("Mediterranean"));
        cookingStylesRepository.save(new CookingStyle("American"));
        cookingStylesRepository.save(new CookingStyle("Asian"));
        cookingStylesRepository.save(new CookingStyle("Italian"));
        cookingStylesRepository.save(new CookingStyle("Arabic"));
        cookingStylesRepository.save(new CookingStyle("Latina"));
    }

}