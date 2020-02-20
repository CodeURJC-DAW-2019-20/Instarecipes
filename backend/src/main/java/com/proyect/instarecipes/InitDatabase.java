package com.proyect.instarecipes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.views.GroupStaff;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
        @Autowired
        private CommentsRepository commentsRepository;
        @Autowired
        private StepsRepository stepsRepository;
        @Autowired
        private ImageService imageService;

        @PostConstruct
        private void initDatabase() throws FileNotFoundException {
                GroupStaff groupStaff = new GroupStaff();

                // Users examples
                User u1 = new User("user1", "pepe@grillo.com", "pass", "Pepe", "Grillo", "Hello World !!", "sida", null,
                                null, "ROLE_USER");
                User u2 = new User("user2", "manu@gmail.com", "pass", "Manuel", "Savater", "Konichiwa people !",
                                "awp's", null, null, "ROLE_USER");
                User u3 = new User("user3", "trevodrap@hotmail.com", "pass", "Trevod", "Rap", "Hello people !",
                                "Toyacos", null, null, "ROLE_USER");
                User u4 = new User("admin", "hola@adios.com", "adminpass", "Hamsa", "Jefe", "Hi people !", "cerdo",
                                null, null, "ROLE_USER", "ROLE_ADMIN");

                Set<User> followers1 = groupStaff.groupFollowers(u2, u3, u4);
                Set<User> following1 = groupStaff.groupFollowing(u2, u3, u4);
                Set<User> followers2 = groupStaff.groupFollowers(u1, u3, u4);
                Set<User> following2 = groupStaff.groupFollowing(u1, u4);

                u1.setFollowers(followers1);
                u2.setFollowers(followers2);
                u3.setFollowers(followers2);
                u4.setFollowers(followers1);

                u1.setFollowing(following1);
                u2.setFollowing(following2);
                u3.setFollowing(following2);
                u4.setFollowing(following1);

                userRepository.save(u1);
                userRepository.save(u2);
                userRepository.save(u3);
                userRepository.save(u4);

                // Ingredients examples
                Ingredient i1 = new Ingredient("Potatoes");
                Ingredient i2 = new Ingredient("Honey");
                Ingredient i3 = new Ingredient("Fish");
                Ingredient i4 = new Ingredient("Tomatoes");
                Ingredient i5 = new Ingredient("Milk");
                Ingredient i6 = new Ingredient("Bread");

                ingredientsRepository.save(i1);
                ingredientsRepository.save(i2);
                ingredientsRepository.save(i3);
                ingredientsRepository.save(i4);
                ingredientsRepository.save(i5);
                ingredientsRepository.save(i6);

                // Categories examples
                Category c1 = new Category("Desserts");
                Category c2 = new Category("Starters");
                Category c3 = new Category("Main");
                Category c4 = new Category("Soups");
                Category c5 = new Category("Salad");
                Category c6 = new Category("Burgers");
                Category c7 = new Category("Pizzas");
                Category c8 = new Category("Smoothies");
                Category c9 = new Category("BreakFast");
                Category c10 = new Category("Brunch");
                Category c11 = new Category("Dinner");

                categoriesRepository.save(c1);
                categoriesRepository.save(c2);
                categoriesRepository.save(c3);
                categoriesRepository.save(c4);
                categoriesRepository.save(c5);
                categoriesRepository.save(c6);
                categoriesRepository.save(c7);
                categoriesRepository.save(c8);
                categoriesRepository.save(c9);
                categoriesRepository.save(c10);
                categoriesRepository.save(c11);

                // Cooking Styles examples
                CookingStyle cs1 = new CookingStyle("Vegan");
                CookingStyle cs2 = new CookingStyle("Vegetarian");
                CookingStyle cs3 = new CookingStyle("Mediterranean");
                CookingStyle cs4 = new CookingStyle("American");
                CookingStyle cs5 = new CookingStyle("Asian");
                CookingStyle cs6 = new CookingStyle("Italian");
                CookingStyle cs7 = new CookingStyle("Arabic");
                CookingStyle cs8 = new CookingStyle("Latina");

                cookingStylesRepository.save(cs1);
                cookingStylesRepository.save(cs2);
                cookingStylesRepository.save(cs3);
                cookingStylesRepository.save(cs4);
                cookingStylesRepository.save(cs5);
                cookingStylesRepository.save(cs6);
                cookingStylesRepository.save(cs7);
                cookingStylesRepository.save(cs8);

                // Recipes examples
                Set<Ingredient> ingredients1 = groupStaff.groupIngredients(i1, i2, i3);
                Set<Ingredient> ingredients2 = groupStaff.groupIngredients(i2, i4, i5, i6);
                Set<Ingredient> ingredients3 = groupStaff.groupIngredients(i2, i4, i5, i6, i1);
                Set<Ingredient> ingredients4 = groupStaff.groupIngredients(i2, i4, i5, i3, i6);

                Set<Category> categories1 = groupStaff.groupCategories(c1, c2, c3);
                Set<Category> categories2 = groupStaff.groupCategories(c2, c4, c5, c6);
                Set<Category> categories3 = groupStaff.groupCategories(c1, c2, c3, c4, c5);
                Set<Category> categories4 = groupStaff.groupCategories(c2, c4);

                Set<CookingStyle> cookingStyles1 = groupStaff.groupCookingStyles(cs1, cs2, cs3);
                Set<CookingStyle> cookingStyles2 = groupStaff.groupCookingStyles(cs2, cs4);

                Recipe r1 = new Recipe(u1, ingredients1, categories1, cookingStyles1, "Homemade Pizza!",
                                "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!",
                                "15 min.", "Hard", "Acids", 3);
                Recipe r2 = new Recipe(u1, ingredients4, categories3, cookingStyles2, "Fish with i dont know",
                                "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese, and an avocado…toss it alltogether, and done. It’s summery, healthy, and so good!",
                                "30 min.", "Hard", "Gluten", 10);
                Recipe r3 = new Recipe(u1, ingredients3, categories4, cookingStyles2, "Avocado Salad",
                                "Your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese, and an avocado…toss it alltogether, and done. It’s summery, healthy, and so good!",
                                "45 min.", "Hard", "Sugar", 22);
                Recipe r4 = new Recipe(u2, ingredients2, categories2, cookingStyles2, "Avocado Salad",
                                "This Sheet Pan Garlic Herb Butter Chicken recipe is low in fat and absolutely incredible. So buttery with so much flavour, it TASTES so sinful yet contains half the fat of a regular butter sauce that no one knows the difference!",
                                "15 min.", "Hard", "Milk", 35);
                Recipe r5 = new Recipe(u2, ingredients1, categories2, cookingStyles2, "Avocado Salad",
                                "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese, and an avocado…toss it alltogether, and done. It’s summery, healthy, and so good!",
                                "1 h.", "Hard", "Honey", 15);
                // Steps for r4(for example)
                Step step11 = new Step(r4, 1,
                                "Preheat oven to 425 degrees F (220 degrees C). Lightly oil a large roasting pan.");
                Step step22 = new Step(r4, 2,
                                "Place chicken pieces in large bowl. Season with salt, oregano, pepper, rosemary, and cayenne pepper. Add fresh lemon juice, olive oil, and garlic. Place potatoes in bowl with the chicken; stir together until chicken and potatoes are evenly coated with marinade.");
                Step step33 = new Step(r4, 3,
                                "Transfer chicken pieces, skin side up, to prepared roasting pan, reserving marinade. Distribute potato pieces among chicken thighs. Drizzle with 2/3 cup chicken broth. Spoon remainder of marinade over chicken and potatoes.");
                // Steps for r5(for example)
                Step step1 = new Step(r5, 1,
                                "Place in preheated oven. Bake in the preheated oven for 20 minutes. Toss chicken and potatoes, keeping chicken skin side up; continue baking until chicken is browned and cooked through, about 25 minutes more. An instant-read thermometer inserted near the bone should read 165 degrees F (74 degrees C). Transfer chicken to serving platter and keep warm.");
                Step step2 = new Step(r5, 2,
                                "Set oven to broil or highest heat setting. Toss potatoes once again in pan juices. Place pan under broiler and broil until potatoes are caramelized, about 3 minutes. Transfer potatoes to serving platter with chicken.");
                Step step3 = new Step(r5, 3,
                                "Place roasting pan on stove over medium heat. Add a splash of broth and stir up browned bits from the bottom of the pan. Strain; spoon juices over chicken and potatoes. Top with chopped oregano.");
                recipesRepository.save(r1);
                recipesRepository.save(r2);
                recipesRepository.save(r3);
                recipesRepository.save(r4);
                recipesRepository.save(r5);
                // Save steps
                stepsRepository.save(step11);
                stepsRepository.save(step22);
                stepsRepository.save(step33);
                stepsRepository.save(step1);
                stepsRepository.save(step2);
                stepsRepository.save(step3);

                try {
                        String path = new File(".").getCanonicalPath();
                        System.out.println("ESTO ES EL DIRECTORIO: " + path);
                } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }

                // Create images / Canonical folder is backend!!!
                File file1 = new File("src/main/resources/static/images/Recipes/chicken_recipe.jpg");
                File file2 = new File("src/main/resources/static/images/Recipes/chicken_potatoes.jpg");
                File file3 = new File("src/main/resources/static/images/Recipes/potatoes_recipe.jpg");
                File file4 = new File("src/main/resources/static/images/Recipes/potatoes_chicken_recipe.jpg");
                File file5 = new File("src/main/resources/static/images/Recipes/recipe_example_7.jpg");
                FileInputStream input1 = new FileInputStream(file1);
                FileInputStream input2 = new FileInputStream(file2);
                FileInputStream input3 = new FileInputStream(file3);
                FileInputStream input4 = new FileInputStream(file4);
                FileInputStream input5 = new FileInputStream(file5);
                MultipartFile imageFile1;
                MultipartFile imageFile2;
                MultipartFile imageFile3;
                MultipartFile imageFile4;
                MultipartFile imageFile5;
        try {
                imageFile1 = new MockMultipartFile("file1", file1.getName(), "image/jpeg", IOUtils.toByteArray(input1));
                imageFile2 = new MockMultipartFile("file2", file2.getName(), "image/jpeg", IOUtils.toByteArray(input2));
                imageFile3 = new MockMultipartFile("file3", file3.getName(), "image/jpeg", IOUtils.toByteArray(input3));
                imageFile4 = new MockMultipartFile("file4", file4.getName(), "image/jpeg", IOUtils.toByteArray(input4));
                imageFile5 = new MockMultipartFile("file5", file5.getName(), "image/jpeg", IOUtils.toByteArray(input5));
                //Save images
                imageService.saveImage("recipes", r1.getId(), imageFile1);
                imageService.saveImage("recipes", r2.getId(), imageFile2);
                imageService.saveImage("recipes", r3.getId(), imageFile3);
                imageService.saveImage("recipes", r4.getId(), imageFile4);
                imageService.saveImage("recipes", r5.getId(), imageFile5);
        }catch(IOException e){
                e.printStackTrace();
                System.out.println("SOMETHING IS WRONG IN THE IMAGES CREATIONS, CHECK InitDatabase.java");
        }
        //Comments: User, Content, Subcomments, Likes
        //template
        /*              ALL THIS IS ONLY FOR RECIPE NUMBER 1                    */
        Comment comment1 = new Comment(u3, "Cool", null, 3, r1);
        Comment comment2 = new Comment(u1, "This is awesome", null, 1, r1);
                Comment comment3 = new Comment(u2, "Do you eat cheesse?", comment2, 2, r1);
                        Comment comment4 = new Comment(u1, "Yes i do", comment3, 4, r1);
                Comment comment5 = new Comment(u4, "Lol what a conversation, so original", comment2, 7, r1);
                Comment comment6 = new Comment(u1, "Shut the fk up idiot", comment2, 0, r1);
        
        //Save coments
        commentsRepository.save(comment1);
        commentsRepository.save(comment2); 
        commentsRepository.save(comment3);
        commentsRepository.save(comment4);
        commentsRepository.save(comment5);
        commentsRepository.save(comment6);

        //THIS SHOULD BE ALWAYS LIKE THIS, THIS WILL UPDATE THE RECIPES WITH THE CORRESPONDING NUMBER OF COMMENTS
        for(Recipe recipe : recipesRepository.findAll()){
                recipe.setN_comments(commentsRepository.countByRecipeId(recipe));
                recipesRepository.save(recipe);
        }
        /* _ _ _ _ _ _ _ _ _ _ ALL THIS IS ONLY FOR RECIPE NUMBER 1 _ _ _ _ _ _ _ _ _ _ */
    }

}