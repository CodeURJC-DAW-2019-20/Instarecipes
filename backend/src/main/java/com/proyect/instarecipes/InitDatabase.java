package com.proyect.instarecipes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.RequestsRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.views.GroupStaff;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
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
    private AllergensRepository allergensRepository;
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private ImageService imageService;

    @PostConstruct
    private void initDatabase() throws IOException {
        GroupStaff groupStaff = new GroupStaff();

        //Users examples
        User u1 = new User("user1", "pepe@grillo.com", "pass", "Pepe", "Grillo", "Hello World !!", "sida", null, null, "ROLE_USER");
        User u2 = new User("user2", "manu@gmail.com", "pass", "Manuel", "Savater", "Konichiwa people !", "awp's", null, null, "ROLE_USER");
        User u3 = new User("user3", "trevodrap@hotmail.com", "pass", "Trevod", "Rap","Hello people !", "Toyacos", null, null, "ROLE_USER");
        User u4 = new User("admin", "hola@adios.com", "adminpass", "Hamsa", "Jefe", "Hi people !", "cerdo", null, null, "ROLE_USER", "ROLE_ADMIN");
        Set<User> followers1 = groupStaff.groupFollowers(u2,u3,u4);
        Set<User> following1 = groupStaff.groupFollowing(u2,u3,u4);
        Set<User> followers2 = groupStaff.groupFollowers(u1,u3,u4);
        Set<User> following2 = groupStaff.groupFollowing(u1,u4);
        //Set followers
        u1.setFollowers(followers1);
        u2.setFollowers(followers2);
        u3.setFollowers(followers2);
        u4.setFollowers(followers1);
        //Set following
        u1.setFollowing(following1);
        u2.setFollowing(following2);
        u3.setFollowing(following2);
        u4.setFollowing(following1);

        //Avatar and backgrounds BOTS
        File avatarBots = new File("src/main/resources/static/images/icons/avatar.jpg");
        File backgroundBots = new File("src/main/resources/static/images/backgrounds/profile_background_example.jpeg");
        FileInputStream aBots = new FileInputStream(avatarBots);
        FileInputStream bBots = new FileInputStream(backgroundBots);
        MultipartFile userAvatar = new MockMultipartFile("file2", avatarBots.getName(), "image/jpeg", IOUtils.toByteArray(aBots));
        MultipartFile userBackground = new MockMultipartFile("file3", backgroundBots.getName(), "image/jpeg", IOUtils.toByteArray(bBots));
        //Set avatars
        u1.setAvatar(true);
        u2.setAvatar(true);
        u3.setAvatar(true);
        u4.setAvatar(true);
        //Set backgrounds
        u1.setBackground(true);
        u2.setBackground(true);
        u3.setBackground(true);
        u4.setBackground(true);
        //Save users
        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        //Save avatars
        imageService.saveImage("avatars", u1.getId(), userAvatar);
        imageService.saveImage("avatars", u2.getId(), userAvatar);
        imageService.saveImage("avatars", u3.getId(), userAvatar);
        imageService.saveImage("avatars", u4.getId(), userAvatar);
        //Save backgrounds
        imageService.saveImage("backgrounds", u1.getId(), userBackground);
        imageService.saveImage("backgrounds", u2.getId(), userBackground);
        imageService.saveImage("backgrounds", u3.getId(), userBackground);
        imageService.saveImage("backgrounds", u4.getId(), userBackground);

        //Ingredients examples
        Ingredient i1 = new Ingredient("Potatoes");
        Ingredient i2 = new Ingredient("Lettuce");
        Ingredient i3 = new Ingredient("Fish");
        Ingredient i4 = new Ingredient("Chicken");
        Ingredient i5 = new Ingredient("Carrots");
        Ingredient i6 = new Ingredient("Pasta");
        Ingredient i7 = new Ingredient("Garlic");
        Ingredient i8 = new Ingredient("Peppers");
        Ingredient i9 = new Ingredient("Tomato Sauce");
        Ingredient i10 = new Ingredient("Lemon");
        Ingredient i11 = new Ingredient("Butter");
        Ingredient i12 = new Ingredient("Radish");
        Ingredient i13 = new Ingredient("Oregano");
        Ingredient i14 = new Ingredient("Vodka");
        Ingredient i15 = new Ingredient("Cheese");

        ingredientsRepository.save(i1);
        ingredientsRepository.save(i2);
        ingredientsRepository.save(i3);
        ingredientsRepository.save(i4);
        ingredientsRepository.save(i5);
        ingredientsRepository.save(i6);
        ingredientsRepository.save(i7);
        ingredientsRepository.save(i8);
        ingredientsRepository.save(i9);
        ingredientsRepository.save(i10);
        ingredientsRepository.save(i11);
        ingredientsRepository.save(i12);
        ingredientsRepository.save(i13);
        ingredientsRepository.save(i14);
        ingredientsRepository.save(i15);

        //Categories examples        
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
        //Save categories
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

        //Cooking Styles examples        
        CookingStyle cs1 = new CookingStyle("Vegan");
        CookingStyle cs2 = new CookingStyle("Vegetarian");
        CookingStyle cs3 = new CookingStyle("Mediterranean");
        CookingStyle cs4 = new CookingStyle("American");
        CookingStyle cs5 = new CookingStyle("Asian");
        CookingStyle cs6 = new CookingStyle("Italian");
        CookingStyle cs7 = new CookingStyle("Arabic");
        CookingStyle cs8 = new CookingStyle("Latina");
        //Save cooking styles
        cookingStylesRepository.save(cs1);
        cookingStylesRepository.save(cs2);
        cookingStylesRepository.save(cs3);
        cookingStylesRepository.save(cs4);
        cookingStylesRepository.save(cs5);
        cookingStylesRepository.save(cs6);
        cookingStylesRepository.save(cs7);
        cookingStylesRepository.save(cs8);

        //Allergen examples
        Allergen a1 = new Allergen("Peanuts");
        Allergen a2 = new Allergen("Crustaceans");
        Allergen a3 = new Allergen("Eggs");
        Allergen a4 = new Allergen("Soybeans");
        Allergen a5 = new Allergen("Milk");
        Allergen a6 = new Allergen("Mustard");
        Allergen a7 = new Allergen("Wheat");
        Allergen a8 = new Allergen("Soy");
        Allergen a9 = new Allergen("Nuts");

        //Save allergens
        allergensRepository.save(a1);
        allergensRepository.save(a2);
        allergensRepository.save(a3);
        allergensRepository.save(a4);
        allergensRepository.save(a5);
        allergensRepository.save(a6);
        allergensRepository.save(a7);
        allergensRepository.save(a8);
        allergensRepository.save(a9);

        //Requests examples
        Request req1 = new Request(u2, "Ingredient", "Apple", null, null, false);
        Request req2 = new Request(u3, "Ingredient", "Potatoes", null, null, true);
        //Save requests
        requestsRepository.save(req1);
        requestsRepository.save(req2);

        //Recipes examples
        Set<Ingredient> ingredients1 = groupStaff.groupIngredients(i1,i4,i7);//pizzita
        Set<Ingredient> ingredients2 = groupStaff.groupIngredients(i7,i10,i11); //fish
        Set<Ingredient> ingredients3 = groupStaff.groupIngredients(i1,i4,i5,i7,i8); //thai
        Set<Ingredient> ingredients4 = groupStaff.groupIngredients(i2,i7,i8,i10,i12); //salad
        Set<Ingredient> ingredients5 = groupStaff.groupIngredients(i6,i7,i8,i9,i13,i14); //pasta

        Set<Category> categories1 = groupStaff.groupCategories(c3,c11);//pizzita
        Set<Category> categories2 = groupStaff.groupCategories(c3,c11); //fish, pasta
        Set<Category> categories3 = groupStaff.groupCategories(c3,c4,c11); //thai
        Set<Category> categories4 = groupStaff.groupCategories(c2,c3,c4,c5,c11);  //salad
         
        Set<CookingStyle> cookingStyles1 = groupStaff.groupCookingStyles(cs1,cs3,cs4); //pizzita
        Set<CookingStyle> cookingStyles2 = groupStaff.groupCookingStyles(cs3); //fish
        Set<CookingStyle> cookingStyles3 = groupStaff.groupCookingStyles(cs5); //thai
        Set<CookingStyle> cookingStyles4 = groupStaff.groupCookingStyles(cs6); //salad

        Set<Allergen> allergens1 = groupStaff.groupAllergens(a7,a8); //pizzita
        Set<Allergen> allergens2 = groupStaff.groupAllergens(a3,a5,a6); //fish
        Set<Allergen> allergens3 = groupStaff.groupAllergens(a8); //chicken
        Set<Allergen> allergens4 = groupStaff.groupAllergens(a5); //chicken
        Set<Allergen> allergens5 = groupStaff.groupAllergens(a9); 

        Recipe r1 = new Recipe(u1, ingredients1, categories1, cookingStyles1, allergens1, "Homemade Pizza!", "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!", 
        "15 min.", "Hard", 3);
        Recipe r2 = new Recipe(u1, ingredients2, categories2, cookingStyles2, allergens2, "Baked Fish with Lemon Cream Sauce", "Yup, just throw it all in one pan, bake it, and you end up with a tender juicy fish in a creamy lemon sauce.", 
        "30 min.", "Hard", 10);
        Recipe r3 = new Recipe(u1, ingredients3, categories3, cookingStyles3, allergens3, "Thai Chicken Soup with Rice Noodles", "Cozy, comforting, and fragrant, this flavor-packed Thai chicken noodle soup will warm you right up.", 
        "45 min.", "Hard", 22);
        Recipe r4 = new Recipe(u2, ingredients4, categories4, cookingStyles4, allergens5, "Avocado Salad", "This salad features ripe avocado slices covered in a fresh lime dressing, topped generously with a contrasting crisp-and-crunchy blend of chopped radish, onion, peppers, and herbs.", 
        "15 min.", "Hard", 35);
        Recipe r5 = new Recipe(u2, ingredients5, categories2, cookingStyles4, allergens4, "Vodka Sauce Pasta (Pasta Party!)", "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese.. And don't worry, It won't taste like vodka!", 
        "1 h.", "Hard", 15);

        //Steps for r4(for example)
        Step step11 = new Step(r4, 1, "Preheat oven to 425 degrees F (220 degrees C). Lightly oil a large roasting pan.");
        Step step22 = new Step(r4, 2, "Place chicken pieces in large bowl. Season with salt, oregano, pepper, rosemary, and cayenne pepper. Add fresh lemon juice, olive oil, and garlic. Place potatoes in bowl with the chicken; stir together until chicken and potatoes are evenly coated with marinade.");
        Step step33 = new Step(r4, 3, "Transfer chicken pieces, skin side up, to prepared roasting pan, reserving marinade. Distribute potato pieces among chicken thighs. Drizzle with 2/3 cup chicken broth. Spoon remainder of marinade over chicken and potatoes.");
        //Steps for r5(for example)
        Step step1 = new Step(r5, 1, "Place in preheated oven. Bake in the preheated oven for 20 minutes. Toss chicken and potatoes, keeping chicken skin side up; continue baking until chicken is browned and cooked through, about 25 minutes more. An instant-read thermometer inserted near the bone should read 165 degrees F (74 degrees C). Transfer chicken to serving platter and keep warm.");
        Step step2 = new Step(r5, 2, "Set oven to broil or highest heat setting. Toss potatoes once again in pan juices. Place pan under broiler and broil until potatoes are caramelized, about 3 minutes. Transfer potatoes to serving platter with chicken.");
        Step step3 = new Step(r5, 3, "Place roasting pan on stove over medium heat. Add a splash of broth and stir up browned bits from the bottom of the pan. Strain; spoon juices over chicken and potatoes. Top with chopped oregano.");
        recipesRepository.save(r1);
        recipesRepository.save(r2);
        recipesRepository.save(r3);
        recipesRepository.save(r4);
        recipesRepository.save(r5);
        //Save steps
        stepsRepository.save(step11);
        stepsRepository.save(step22);
        stepsRepository.save(step33);
        stepsRepository.save(step1);
        stepsRepository.save(step2);
        stepsRepository.save(step3);

        // Create images / Canonical folder is backend!!!
        File file1 = new File("src/main/resources/static/images/Recipes/recipe_example_1.jpg");
        File file2 = new File("src/main/resources/static/images/Recipes/recipe_example_6.jpg");
        File file3 = new File("src/main/resources/static/images/Recipes/recipe_example_9.jpg");
        File file4 = new File("src/main/resources/static/images/Recipes/recipe_example_4.jpg");
        File file5 = new File("src/main/resources/static/images/Recipes/recipe_example_8.jpg");
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
        
        //Comments: User, Content, Subcomments, Likes
        //template
        /*              ALL THIS IS ONLY FOR RECIPE NUMBER 1               */
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