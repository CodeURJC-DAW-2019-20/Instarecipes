package com.proyect.instarecipes.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.HashSet;

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
        User u1 = new User("pepegrillo", "pepe@grillo.com", "pass", "Pepe", "Grillo", "Hello World!! I hope you enjoy my recipes!", "Nuts", null, null, "ROLE_USER");
        User u2 = new User("manusav96", "manu@gmail.com", "pass", "Manuel", "Savater", "Konichiwa people! I eat sushi every single day.", "Milk", null, null, "ROLE_USER");
        User u3 = new User("trevrap", "trevodrap@hotmail.com", "pass", "Trevod", "Rap", "I'm a big fan of desserts, follow me and you will see it!", "Peanuts", null, null, "ROLE_USER");
        User u4 = new User("admin", "hola@adios.com", "adminpass", "Hamsa", "Jefe", "Hi people! Don't mess with me. I'm the boss hehe.", "cerdo", null, null, "ROLE_USER", "ROLE_ADMIN");
        User u5 = new User("pepitoram", "pepito@gmail.com","pass","Pedro","Ramírez","This isn't real","Soy",null,null,"ROLE_USER");

        User u6 = new User("anuelbb", "anuelAA@gmail.com", "pass", "Anuel", "AA", "Mera woooo", "Mustard", null, null, "ROLE_USER");
        User u7 = new User("heisenberg", "badbunny@gmail.com", "pass", "Benito", "Boss", "#YHLQMDLG", "Wheat", null, null, "ROLE_USER");
        User u8 = new User("user8", "ozuna@hotmail.com", "pass", "El Negrito", "Ojos Claros","Woooo", "Peanuts", null, null, "ROLE_USER");
        User u9 = new User("oceloteLVP", "ocelote@dios.com", "pass", "Spanish", "Rocket", "Hi mum i'm famous", null, null, null, "ROLE_USER");
        User u10 = new User("rodriram", "s1mple@gmail.com","pass","Rodrigo","Ramírez","wtf is this user",null ,null,null,"ROLE_USER");
        User u11 = new User("ifelse", "electronic@grillo.com", "pass", "Pepe", "Grillo", "Python>Java", "Eggs", null, null, "ROLE_USER");

        Set<User> following1 = groupStaff.groupFollowing(u2,u4,u6,u7,u10);
        Set<User> following2 = groupStaff.groupFollowing(u1,u3,u5,u8,u9);
        Set<User> following3 = groupStaff.groupFollowing(u2,u4,u6,u7,u10);
        Set<User> following4 = groupStaff.groupFollowing(u1,u3,u5,u8,u9);
        Set<User> following5 = groupStaff.groupFollowing(u1,u2,u4,u6,u7,u10);
        Set<User> following6 = groupStaff.groupFollowing(u1,u3,u5,u8,u9);
        Set<User> following7 = groupStaff.groupFollowing(u1,u2);
        Set<User> following8 = groupStaff.groupFollowing(u1);
        Set<User> following9 = groupStaff.groupFollowing(u2);
        Set<User> following10 = groupStaff.groupFollowing(u1);

        Set<User> followers1 = groupStaff.groupFollowers(u1,u3,u5);
        Set<User> followers2 = groupStaff.groupFollowers(u2,u4,u6);
        Set<User> followers3 = groupStaff.groupFollowers(u1,u3,u5,u7,u9);
        Set<User> followers4 = groupStaff.groupFollowers(u2,u4,u5,u6,u7,u8,u10);

         //Set following
        u1.setFollowing(following1);
        u2.setFollowing(following2);
        u3.setFollowing(following3);
        u4.setFollowing(following4);
        u5.setFollowing(following5);
        u6.setFollowing(following6);
        u7.setFollowing(following7);
        u8.setFollowing(following8);
        u9.setFollowing(following9);
        u10.setFollowing(following10);

        // Set followers
        u1.setFollowers(followers4);
        u2.setFollowers(followers3);
        u3.setFollowers(followers2);
        u4.setFollowers(followers1);
        u5.setFollowers(followers2);

        u6.setFollowers(followers1);
        u7.setFollowers(followers1);
        u8.setFollowers(followers2);
        u9.setFollowers(followers2);
        u10.setFollowers(followers1);

        //likes examples
        Set<User> likes1 = groupStaff.groupLikes(u1,u2,u3,u4,u5,u7,u8,u10,u11);
        Set<User> likes2 = groupStaff.groupLikes(u1,u3,u4,u5,u6,u7,u8,u9,u10);
        Set<User> likes3 = groupStaff.groupLikes(u2,u4);
        Set<User> likes4 = groupStaff.groupLikes(u3);
        Set<User> likes5 = groupStaff.groupLikes(u1,u3,u5,u7,u9,u11);
        Set<User> likes6 = groupStaff.groupLikes(u10,u11);
 
         //Avatar and backgrounds BOTS
        /*File avatarBots = new File("src/main/resources/static/images/icons/avatar.jpg");*/
        File backgroundBots = new File("src/main/resources/static/images/backgrounds/profile_background_example.jpeg");
        //FileInputStream aBots = new FileInputStream(avatarBots);
        FileInputStream bBots = new FileInputStream(backgroundBots);
        //MultipartFile userAvatar = new MockMultipartFile("file2", avatarBots.getName(), "image/jpeg", IOUtils.toByteArray(aBots));
        MultipartFile userBackground = new MockMultipartFile("file3", backgroundBots.getName(), "image/jpeg", IOUtils.toByteArray(bBots));
        
        //Set avatars
        u1.setAvatar(true);
        u2.setAvatar(true);
        u3.setAvatar(true);
        u4.setAvatar(true);
        u5.setAvatar(true);

        u6.setAvatar(true);
        u7.setAvatar(true);
        u8.setAvatar(true);
        u9.setAvatar(true);
        u10.setAvatar(true);

        u11.setAvatar(true);

        //Set backgrounds
        u1.setBackground(true);
        u2.setBackground(true);
        u3.setBackground(true);
        u4.setBackground(true);
        u5.setBackground(true);

        u6.setBackground(true);
        u7.setBackground(true);
        u8.setBackground(true);
        u9.setBackground(true);
        u10.setBackground(true);
     
        // //Save users
        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        userRepository.save(u5);

        userRepository.save(u6);
        userRepository.save(u7);
        userRepository.save(u8);
        userRepository.save(u9);
        userRepository.save(u10);
        userRepository.save(u11);
        //Save avatars
        /*imageService.saveImage("avatars", u1.getId(), userAvatar);
        imageService.saveImage("avatars", u2.getId(), userAvatar);
        imageService.saveImage("avatars", u3.getId(), userAvatar);
        imageService.saveImage("avatars", u4.getId(), userAvatar);
        imageService.saveImage("avatars", u5.getId(), userAvatar);
        imageService.saveImage("avatars", u6.getId(), userAvatar);
        imageService.saveImage("avatars", u7.getId(), userAvatar);
        imageService.saveImage("avatars", u8.getId(), userAvatar);
        imageService.saveImage("avatars", u9.getId(), userAvatar);
        imageService.saveImage("avatars", u10.getId(), userAvatar);
        imageService.saveImage("avatars", u11.getId(), userAvatar);*/


        //Save backgrounds
        imageService.saveImage("backgrounds", u1.getId(), userBackground);
        imageService.saveImage("backgrounds", u2.getId(), userBackground);
        imageService.saveImage("backgrounds", u3.getId(), userBackground);
        imageService.saveImage("backgrounds", u4.getId(), userBackground);
        imageService.saveImage("backgrounds", u5.getId(), userBackground);
        imageService.saveImage("backgrounds", u6.getId(), userBackground);
        imageService.saveImage("backgrounds", u7.getId(), userBackground);
        imageService.saveImage("backgrounds", u8.getId(), userBackground);
        imageService.saveImage("backgrounds", u9.getId(), userBackground);
        imageService.saveImage("backgrounds", u10.getId(), userBackground);
        imageService.saveImage("backgrounds", u11.getId(), userBackground);

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
        Ingredient i16 = new Ingredient("Raw Cashes");
        Ingredient i17 = new Ingredient("Tamari");
        Ingredient i18 = new Ingredient("Water");
        Ingredient i19 = new Ingredient("Couscous");
        Ingredient i20 = new Ingredient("Olives");
        Ingredient i21 = new Ingredient("Chocolate");
        Ingredient i22 = new Ingredient("Vanilla");
        Ingredient i23 = new Ingredient("Sugar");

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
        ingredientsRepository.save(i16);
        ingredientsRepository.save(i17);
        ingredientsRepository.save(i18);
        ingredientsRepository.save(i19);
        ingredientsRepository.save(i20);
        ingredientsRepository.save(i21);
        ingredientsRepository.save(i22);
        ingredientsRepository.save(i23);

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
        Category c12 = new Category("Sauce");
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
        categoriesRepository.save(c12);

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
        Allergen a10 = new Allergen("Cashews");
        Allergen a11 = new Allergen("Olives");
        Allergen a12 = new Allergen("Chocolate");

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
        allergensRepository.save(a10);
        allergensRepository.save(a11);
        allergensRepository.save(a12);

        //Requests examples
        Request req1 = new Request(u2, "Ingredient", "Apple", null, null, false);
        Request req2 = new Request(u3, "Ingredient", "Potatoes", null, null, true);
        //Save requests
        requestsRepository.save(req1);
        requestsRepository.save(req2);

        //Recipes examples
        Set<Ingredient> ingredients1 = groupStaff.groupIngredients(i1,i4,i7);//pizzita
        Set<Ingredient> ingredients2 = groupStaff.groupIngredients(i3,i7,i10,i11); //fish
        Set<Ingredient> ingredients3 = groupStaff.groupIngredients(i1,i4,i5,i7,i8); //thai
        Set<Ingredient> ingredients4 = groupStaff.groupIngredients(i2,i7,i8,i10,i12); //salad
        Set<Ingredient> ingredients5 = groupStaff.groupIngredients(i6,i7,i8,i9,i13,i14); //pasta
        Set<Ingredient> ingredients6 = groupStaff.groupIngredients(i15,i16,i17,i18); //cheese
        Set<Ingredient> ingredients7 = groupStaff.groupIngredients(i7,i8,i19,i20); //couscous
        Set<Ingredient> ingredients8 = groupStaff.groupIngredients(i21,i22,i23); // Chocolate

        Set<Category> categories1 = groupStaff.groupCategories(c3,c11);//pizzita
        Set<Category> categories2 = groupStaff.groupCategories(c3,c11); //fish, pasta
        Set<Category> categories3 = groupStaff.groupCategories(c3,c4,c11); //thai
        Set<Category> categories4 = groupStaff.groupCategories(c2,c3,c4,c5,c11);  //salad
        Set<Category> categories5 = groupStaff.groupCategories(c12); //sauce
        Set<Category> categories6 = groupStaff.groupCategories(c3); // main
        Set<Category> categories7 = groupStaff.groupCategories(c1); //Dessert
         
        Set<CookingStyle> cookingStyles1 = groupStaff.groupCookingStyles(cs1,cs3,cs4); //pizzita
        Set<CookingStyle> cookingStyles2 = groupStaff.groupCookingStyles(cs3); //fish
        Set<CookingStyle> cookingStyles3 = groupStaff.groupCookingStyles(cs5); //thai
        Set<CookingStyle> cookingStyles4 = groupStaff.groupCookingStyles(cs6); //salad
        Set<CookingStyle> cookingStyles5 = groupStaff.groupCookingStyles(cs1); //vegan

        Set<Allergen> allergens1 = groupStaff.groupAllergens(a7,a8); //pizzita
        Set<Allergen> allergens2 = groupStaff.groupAllergens(a3,a5,a6); //fish
        Set<Allergen> allergens3 = groupStaff.groupAllergens(a8); //chicken
        Set<Allergen> allergens4 = groupStaff.groupAllergens(a5); //chicken
        Set<Allergen> allergens5 = groupStaff.groupAllergens(a9); //nuts
        Set<Allergen> allergens6 = groupStaff.groupAllergens(a10); // Cashews
        Set<Allergen> allergens7 = groupStaff.groupAllergens(a11); //olives
        Set<Allergen> allergens8 = groupStaff.groupAllergens(a12); //chocolate

        Recipe r1 = new Recipe(u1, ingredients1, categories1, cookingStyles1, allergens1, "Homemade Pizza!", "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!", 
        "15 min.", "Hard", likes1);
        Recipe r2 = new Recipe(u1, ingredients2, categories2, cookingStyles2, allergens2, "Baked Fish with Lemon Cream Sauce", "Yup, just throw it all in one pan, bake it, and you end up with a tender juicy fish in a creamy lemon sauce.", 
        "30 min.", "Hard", likes2);
        Recipe r3 = new Recipe(u1, ingredients3, categories3, cookingStyles3, allergens3, "Thai Chicken Soup with Rice Noodles", "Cozy, comforting, and fragrant, this flavor-packed Thai chicken noodle soup will warm you right up.", 
        "45 min.", "Hard", likes3);
        Recipe r4 = new Recipe(u2, ingredients4, categories4, cookingStyles4, allergens5, "Avocado Salad", "This salad features ripe avocado slices covered in a fresh lime dressing, topped generously with a contrasting crisp-and-crunchy blend of chopped radish, onion, peppers, and herbs.", 
        "15 min.", "Hard", likes4);
        Recipe r5 = new Recipe(u2, ingredients5, categories2, cookingStyles4, allergens4, "Vodka Sauce Pasta (Pasta Party!)", "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese.. And don't worry, It won't taste like vodka!", 
        "1 h.", "Hard", likes3);
        Recipe r6 = new Recipe(u4, ingredients6, categories5, cookingStyles5,allergens6, "Cheddar Cheese Sauce","Everyone loves cheese sauce over veggies, or for dipping. But of course there are all the pitfalls of eating cow dairy products. Here is a raw, live, vegan alternative that really stands up for applause!",
        "1 h.","Easy",likes5);
        Recipe r7 = new Recipe(u1, ingredients7,categories6, cookingStyles5,allergens7, "Olives and Sun-Dried Tomato Couscous", "A delicate, flavorful dish that will satisfy vegans and carnivores alike!.",
        "1 h.","Medium",likes6);
        Recipe r8 = new Recipe(u3, ingredients8, categories7, cookingStyles5, allergens8, "Vegan Chocolate Ice Cream", "You are making hummus or some other dish with chickpeas and you are just wasting the chickpea water? How dare you! Didn't you know it can form the basis of some the most delicious, light, and foamy vegan ice creams and mousses?",
        "30 min.","Medium", likes2);
        r1.setImage(true);
        r2.setImage(true);
        r3.setImage(true);
        r4.setImage(true);
        r5.setImage(true);
        r6.setImage(true);
        r7.setImage(true);
        r8.setImage(true);
        recipesRepository.save(r1);
        recipesRepository.save(r2);
        recipesRepository.save(r3);
        recipesRepository.save(r4);
        recipesRepository.save(r5);
        recipesRepository.save(r6);
        recipesRepository.save(r7);
        recipesRepository.save(r8);

        //Save steps
         //Steps for r4(for example)
         Step step11 = new Step(r4, 1, "Preheat oven to 425 degrees F (220 degrees C). Lightly oil a large roasting pan.");
         Step step22 = new Step(r4, 2, "Place chicken pieces in large bowl. Season with salt, oregano, pepper, rosemary, and cayenne pepper. Add fresh lemon juice, olive oil, and garlic. Place potatoes in bowl with the chicken; stir together until chicken and potatoes are evenly coated with marinade.");
         Step step33 = new Step(r4, 3, "Transfer chicken pieces, skin side up, to prepared roasting pan, reserving marinade. Distribute potato pieces among chicken thighs. Drizzle with 2/3 cup chicken broth. Spoon remainder of marinade over chicken and potatoes.");
         //Steps for r5(for example)
         Step step1 = new Step(r5, 1, "Place in preheated oven. Bake in the preheated oven for 20 minutes. Toss chicken and potatoes, keeping chicken skin side up; continue baking until chicken is browned and cooked through, about 25 minutes more. An instant-read thermometer inserted near the bone should read 165 degrees F (74 degrees C). Transfer chicken to serving platter and keep warm.");
         Step step2 = new Step(r5, 2, "Set oven to broil or highest heat setting. Toss potatoes once again in pan juices. Place pan under broiler and broil until potatoes are caramelized, about 3 minutes. Transfer potatoes to serving platter with chicken.");
         Step step3 = new Step(r5, 3, "Place roasting pan on stove over medium heat. Add a splash of broth and stir up browned bits from the bottom of the pan. Strain; spoon juices over chicken and potatoes. Top with chopped oregano.");
         //Steps for r6(for example)
         Step step61 = new Step(r6, 1,"Place cashews in a large bowl. Pour enough water over cashews to cover.");
         Step step62 = new Step(r6, 2,"Cover the bowl and soak cashews overnight, at least 12 hours, then drain the water from cashews; place cashews in a blender. For a tangier cheddar flavor add 1/4 cup freshly squeezed lemon juice to cashews. Pour enough water to come to 1/4-inch below of the top of the cashews. Blend until partially smooth");
         Step step63 = new Step(r6, 3,"Place nutritional yeast, onion, sunflower seed oil, salt, garlic powder, tamari sauce, turmeric, and cayenne pepper in blender. Blend until smooth.");
         //Steps for r7(for example)
         Step step71 = new Step(r7, 1,"Bring 1 1/4 cup vegetable broth and water to a boil in a saucepan, stir in couscous, and mix in salt and black pepper. Reduce heat to low and simmer until liquid is absorbed, about 8 minutes.");
         Step step72 = new Step(r7, 2,"Heat 3 tablespoons olive oil in a skillet over medium-high heat; stir in pine nuts and cook, stirring frequently, until pine nuts smell toasted and are golden brown, about 1 minute. Remove from heat.");
         Step step73 = new Step(r7, 3,"Heat remaining 2 tablespoons olive oil in a saucepan; cook and stir garlic and shallot in the hot oil until softened, about 2 minutes. Stir black olives and sun-dried tomatoes into garlic mixture and cook until heated through, 2 to 3 minutes, stirring often. Slowly pour in 1 cup vegetable broth and bring mixture to a boil. Reduce heat to low and simmer until sauce has reduced, 8 to 10 minutes.");
        
        //Steps for r8
        Step step81 = new Step(r8,1,"Melt chocolate in top of a double boiler over simmering water, stirring frequently and scraping down the sides with a rubber spatula to avoid scorching. Let cool slightly, about 10 minutes.");
        Step step82 = new Step(r8,2,"Pour aquafaba into the bowl of a stand mixer fitted with a whisk attachment. Beat on high speed until fluffy and quadrupled in volume, about 1 minute. Add xanthan gum and beat for 30 seconds. Add confectioners' sugar and vanilla sugar; continue beating until foam is firm and glossy, about 2 minutes more.");
        Step step83 = new Step(r8,3,"Fold melted chocolate gently into whipped foam until thoroughly incorporated. Transfer to a lidded container.");


        stepsRepository.save(step11);
        stepsRepository.save(step22);
        stepsRepository.save(step33);

        stepsRepository.save(step1);
        stepsRepository.save(step2);
        stepsRepository.save(step3);

        stepsRepository.save(step61);
        stepsRepository.save(step62);
        stepsRepository.save(step63);

        stepsRepository.save(step71);
        stepsRepository.save(step72);
        stepsRepository.save(step73);

        stepsRepository.save(step81);
        stepsRepository.save(step82);
        stepsRepository.save(step83);
        // Create images / Canonical folder is backend!!!
        File file1 = new File("src/main/resources/static/images/Recipes/recipe_example_1.jpg");
        File file2 = new File("src/main/resources/static/images/Recipes/recipe_example_6.jpg");
        File file3 = new File("src/main/resources/static/images/Recipes/recipe_example_9.jpg");
        File file4 = new File("src/main/resources/static/images/Recipes/recipe_example_4.jpg");
        File file5 = new File("src/main/resources/static/images/Recipes/recipe_example_8.jpg");
        File file6 = new File("src/main/resources/static/images/Recipes/vegancheese.jpg");
        File file7 = new File("src/main/resources/static/images/Recipes/couscous.jpg");
        File file8 = new File("src/main/resources/static/images/Recipes/chocolate.jpg");

        FileInputStream input1 = new FileInputStream(file1);
        FileInputStream input2 = new FileInputStream(file2);
        FileInputStream input3 = new FileInputStream(file3);
        FileInputStream input4 = new FileInputStream(file4);
        FileInputStream input5 = new FileInputStream(file5);
        FileInputStream input6 = new FileInputStream(file6);
        FileInputStream input7 = new FileInputStream(file7);
        FileInputStream input8 = new FileInputStream(file8);
        MultipartFile imageFile1;
        MultipartFile imageFile2;
        MultipartFile imageFile3;
        MultipartFile imageFile4;
        MultipartFile imageFile5;
        MultipartFile imageFile6;
        MultipartFile imageFile7;
        MultipartFile imageFile8;
        imageFile1 = new MockMultipartFile("file1", file1.getName(), "image/jpeg", IOUtils.toByteArray(input1));
        imageFile2 = new MockMultipartFile("file2", file2.getName(), "image/jpeg", IOUtils.toByteArray(input2));
        imageFile3 = new MockMultipartFile("file3", file3.getName(), "image/jpeg", IOUtils.toByteArray(input3));
        imageFile4 = new MockMultipartFile("file4", file4.getName(), "image/jpeg", IOUtils.toByteArray(input4));
        imageFile5 = new MockMultipartFile("file5", file5.getName(), "image/jpeg", IOUtils.toByteArray(input5));
        imageFile6 = new MockMultipartFile("file6", file6.getName(), "image/jpeg", IOUtils.toByteArray(input6));
        imageFile7 = new MockMultipartFile("file7", file7.getName(), "image/jpeg", IOUtils.toByteArray(input7));
        imageFile8 = new MockMultipartFile("file8", file8.getName(), "image/jpeg", IOUtils.toByteArray(input8));
        //Save images
        imageService.saveImage("recipes", r1.getId(), imageFile1);
        imageService.saveImage("recipes", r2.getId(), imageFile2);
        imageService.saveImage("recipes", r3.getId(), imageFile3);
        imageService.saveImage("recipes", r4.getId(), imageFile4);
        imageService.saveImage("recipes", r5.getId(), imageFile5);
        imageService.saveImage("recipes", r6.getId(), imageFile6);
        imageService.saveImage("recipes", r7.getId(), imageFile7);
        imageService.saveImage("recipes", r8.getId(), imageFile8);
        
        //Comments: User, Content, Subcomments, Likes
        //template
        Set<User> likesC0 = new HashSet<>();
        likesC0.add(u1);
        likesC0.add(u2);
        Set<User> likesC1 = new HashSet<>();
        likesC1.add(u1);
        likesC1.add(u2);
        likesC1.add(u3);
        Set<User> likesC2 = new HashSet<>();
        likesC2.add(u1);
        likesC2.add(u2);
        likesC2.add(u3);
        likesC2.add(u4);

        /*              ALL THIS IS ONLY FOR RECIPE NUMBER 1               */
        Comment comment1 = new Comment(u3, "Cool", null, r1, false, false, likesC0);
        comment1.setLikes(2);
        Comment comment2 = new Comment(u1, "This is awesome", null, r1, false, false, likesC0);
        comment2.setLikes(2);
        
        Comment comment3 = new Comment(u2, "Do you eat cheesse?", null, r1, false, true, likesC1);
        comment3.setLikes(3);
        Comment comment4 = new Comment(u1, "Yes i do", null, r1, false, true, null);
        Comment comment5 = new Comment(u4, "Lol what a conversation, so original ADMINLIKE", null, r1, false, true, likesC2);
        comment5.setLikes(4);
        Comment comment6 = new Comment(u1, "Shut the fk up idiot", null, r1, false, true, null);

        Comment comment7 = new Comment(u2, "What happened in step 3?", null, r1, false, false, null);
        Comment comment8 = new Comment(u1, "Oh yes the comments are alright now ADMINLIKE", null, r1, true, false, likesC2);
        comment8.setLikes(4);
        Comment comment9 = new Comment(u4, "Hello @trevodrap", null, r1, true, false, null);
        Comment comment10 = new Comment(u1, "Hi m8 subscribe @trevodrap in my youtube channel", null, r1, false, true, null);
        
        //Save subcomments
        commentsRepository.save(comment1);
        commentsRepository.save(comment3);
        commentsRepository.save(comment4);
        commentsRepository.save(comment5);
        commentsRepository.save(comment6);
        commentsRepository.save(comment7);
        commentsRepository.save(comment9);
        commentsRepository.save(comment10);
        
        Set<Comment> subComments2 = groupStaff.groupComments(comment3,comment4,comment5,comment6);
        // Set<Comment> subComments3 = groupStaff.groupComments(comment9);
        Set<Comment> subComments4 = groupStaff.groupComments(comment9, comment10);
        //Save coments
        comment2.setHasSubcomments(true);
        comment2.setSubComments(subComments2);
        commentsRepository.save(comment2);

        comment8.setHasSubcomments(true);
        comment8.setSubComments(subComments4);
        commentsRepository.save(comment8);

        //THIS SHOULD BE ALWAYS LIKE THIS, THIS WILL UPDATE THE RECIPES WITH THE CORRESPONDING NUMBER OF COMMENTS
        for(Recipe recipe : recipesRepository.findAll()){
                recipe.setN_comments(commentsRepository.countByRecipeId(recipe));
                recipesRepository.save(recipe);
        }
        /* _ _ _ _ _ _ _ _ _ _ ALL THIS IS ONLY FOR RECIPE NUMBER 1 _ _ _ _ _ _ _ _ _ _ */
    }

 }