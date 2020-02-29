package com.proyect.instarecipes.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

@Controller
public class IndexController {

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;
    @Autowired
    private AllergensRepository allergensRepository;

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private ImageService imageService;

    
    @GetMapping("/")
    public String indexPage(Model model) {
        //we create a list based on the database info!
        List<Recipe> recipes = recipesRepository.findAll();
        for(Recipe r : recipes){
            r.setN_comments(commentsRepository.countByRecipeId(r));
        }
        recipesRepository.flush();
        List<Ingredient> allIngredients = ingredientsRepository.findAll();
        List<CookingStyle> allCookingStyles = cookingStylesRepository.findAll();
        List<Category> allCategories = categoriesRepository.findAll();
        List<Allergen> allAllergens = allergensRepository.findAll();

        model.addAttribute("size", recipes.size());
        model.addAttribute("recipes", recipes);
        personalFilter(model);

        model.addAttribute("ingredientsList", allIngredients);
        model.addAttribute("cookingStylesList", allCookingStyles);
        model.addAttribute("categoriesList", allCategories);
        model.addAttribute("allergensList", allAllergens);
        if(userSession.isLoggedUser()){
            model.addAttribute("id", userSession.getLoggedUser().getId());
        }
        
        return "index";
    }

    
    @GetMapping("/index")
    public String indexedPage(Model model) {
        List<Recipe> recipes = recipesRepository.findAll();
        for(Recipe r : recipes){
            r.setN_comments(commentsRepository.countByRecipeId(r));
        }
        recipesRepository.flush();
        List<Ingredient> allIngredients = ingredientsRepository.findAll();
        List<CookingStyle> allCookingStyles = cookingStylesRepository.findAll();
        List<Category> allCategories = categoriesRepository.findAll();
        List<Allergen> allAllergens = allergensRepository.findAll();

        model.addAttribute("size", recipes.size());
        model.addAttribute("recipes", recipes);
        personalFilter(model);

        model.addAttribute("ingredientsList", allIngredients);
        model.addAttribute("cookingStylesList", allCookingStyles);
        model.addAttribute("categoriesList", allCategories);
        model.addAttribute("allergensList", allAllergens);
        model.addAttribute("id", userSession.getLoggedUser().getId());
        
        return "index";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/signUp")
    public String signupPage(Model model){
        List<Allergen> allAllergensSingUp = allergensRepository.findAll();
        model.addAttribute("allergensSignUp", allAllergensSingUp);
        System.out.println(allAllergensSingUp);
        
        return "signUp";
    }
    @GetMapping("/search")
    public String searchPage(){
        return "search-page";
    }
    @ModelAttribute
	public void addAttributes(Model model) {
		boolean logged = userSession.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        
        if(logged){             
			model.addAttribute("user",userSession.getLoggedUser().getUsername());
            model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		}
	}

    @PostMapping("/")
    public String postRecipe(Model model, Recipe recipe, @RequestParam MultipartFile imageFile, 
    @RequestParam String ingredientsString, @RequestParam String categoriesString, 
    @RequestParam String firstStepString, @RequestParam(required = false) String stepsString, 
    @RequestParam(required = false, value = "allImages") MultipartFile[] allImages, 
    @RequestParam(required = false, value = "withImage") String withImage) throws IOException{
        
        Recipe r = recipe;
        int i = 2;
        System.out.println("Booleanos: " + withImage);
// Ingredients selector //
        List<String> listOfIngs = Arrays.asList(ingredientsString.split(","));
        Set<Ingredient> lastIngs = new HashSet<Ingredient>();
        for(String ings : listOfIngs){
            Optional<Ingredient> ingredient = ingredientsRepository.findByIngredient(ings);
            if(ingredient != null){
                lastIngs.add(ingredient.get());
            }
        }
// Categories selector //
        List<String> listOfCats = Arrays.asList(categoriesString.split(","));
        Set<Category> lastCats = new HashSet<Category>();
        for(String cats : listOfCats){
            Optional<Category> category = categoriesRepository.findByCategory(cats);
            if(category != null){
                lastCats.add(category.get());
            }
        }
//Sets in recipe
        r.setCategories(lastCats);
        r.setIngredients(lastIngs);
        r.setImage(true);
        r.setUsername(userSession.getLoggedUser());
        recipesRepository.save(r);
        imageService.saveImage("recipes", r.getId(), imageFile);
//Steps selector
        int j = 0;
        stepsRepository.save(new Step(r, 1, firstStepString));
        if(withImage.length()>0){
            String stp = withImage.substring(0, withImage.length()-1);
            List<String> listOfBools = Arrays.asList(stp.split(","));
            System.out.println("Array de booleanos: " + listOfBools);
            if(stepsString != null){
                List<String> listOfSteps = Arrays.asList(stepsString.split("ab#12#45-3,"));
                for(String steps : listOfSteps){
                    if(steps != null){
                        Step step_n = new Step(r, i, steps);
                        if(listOfBools.get(j).equalsIgnoreCase("1")){
                            imageService.saveImage("recipes/steps/"+r.getId(), j+2, allImages[j]);
                           step_n.setImage(true);
                        }else{
                           step_n.setImage(false);
                        }
                        stepsRepository.save(step_n);
                        j++;
                        i++;
                    }
                }
            }
        }
//SHOWING ALL ELEMENTS TO MUSTACHE
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        model.addAttribute("size", recipes.size());
        List<Recipe> subRecipe = recipes.subList(0, 3);
        model.addAttribute("subRecipe",subRecipe);
        List<Comment> comments = commentsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_comments", comments.size());

        List<Ingredient> allIngredients = ingredientsRepository.findAll();
        List<CookingStyle> allCookingStyles = cookingStylesRepository.findAll();
        List<Category> allCategories = categoriesRepository.findAll();
        List<Allergen> allAllergens = allergensRepository.findAll();

        model.addAttribute("ingredientsList", allIngredients);
        model.addAttribute("cookingStylesList", allCookingStyles);
        model.addAttribute("categoriesList", allCategories);
        model.addAttribute("allergensList", allAllergens);
        model.addAttribute("id", userSession.getLoggedUser().getId());

        return "index";
    }

    public void personalFilter(Model model){

        List<Recipe> recipes = recipesRepository.FindByLikes();
        boolean logged = userSession.isLoggedUser();    
        ArrayList<Recipe> filtered = new ArrayList<Recipe>(3); //list to show on index.html
        if(logged){
            User user = userSession.getLoggedUser();
            String myAllergen =user.getAllergens(); // allergen's user
            for(int pubs=0; pubs<recipes.size();pubs++){
                Set<Allergen> ReciAllergens = recipes.get(pubs).getAllergens();
                boolean check=false;
                for(Allergen a : ReciAllergens){
                    String allergen = a.getAllergen();
                    if(allergen.equalsIgnoreCase(myAllergen))
                        check = true;
                }
                if(!check)
                    filtered.add(recipes.get(pubs));     
            }
            List<Recipe> filteredfinally= new ArrayList<>();
            int max = 3; // our max trending recipes
            for(int i=0;i<max;i++){
                filteredfinally.add(filtered.get(i));
            }
            model.addAttribute("filteredRecipe", filteredfinally);
        }
        else{
            List<Recipe> xdList= recipesRepository.FindByLikes();
            List<Recipe> notLogged = new ArrayList<>();
            int maX = 3; // our max trending recipes
            for(int i=0;i<maX;i++){
                notLogged.add(xdList.get(i));
            }
            model.addAttribute("notTrending", notLogged);
        }
    }


}