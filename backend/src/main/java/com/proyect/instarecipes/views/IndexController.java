package com.proyect.instarecipes.views;

import java.io.File;
import java.io.FileInputStream;
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

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
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
        List<Ingredient> allIngredients = ingredientsRepository.findAll();
        List<CookingStyle> allCookingStyles = cookingStylesRepository.findAll();
        List<Category> allCategories = categoriesRepository.findAll();
        List<Allergen> allAllergens = allergensRepository.findAll();
        
        model.addAttribute("size", recipes.size());
        model.addAttribute("recipes", recipes);
        model.addAttribute("ingredientsList", allIngredients);
        model.addAttribute("cookingStylesList", allCookingStyles);
        model.addAttribute("categoriesList", allCategories);
        model.addAttribute("allergensList", allAllergens);
        
        personalFilter(model);
        return "index";
    }

    
    @GetMapping("/index")
    public String indexedPage(Model model) {
        List<Recipe> recipes = recipesRepository.findAll();
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
    @RequestParam(required = false) MultipartFile... allImages) throws IOException{
        System.out.println("IMAGEN 1: " + imageFile);
        System.out.println("IMAGEN 2: " + allImages + " SIZE: " + allImages.length);
        Recipe r = recipe;
        int i = 2;
        // Ingredients selector //
        List<String> listOfIngs = Arrays.asList(ingredientsString.split(","));
        Set<Ingredient> lastIngs = new HashSet<Ingredient>();
        for(String ings : listOfIngs){
            Optional<Ingredient> ingredient = ingredientsRepository.findByIngredient(ings);
            if(ingredient != null){
                lastIngs.add(ingredient.get());
            }
        }
        // ---------------------------------------------------------------------------- //
        // Ingredients selector //
        List<String> listOfCats = Arrays.asList(categoriesString.split(","));
        Set<Category> lastCats = new HashSet<Category>();
        for(String cats : listOfCats){
            Optional<Category> category = categoriesRepository.findByCategory(cats);
            if(category != null){
                lastCats.add(category.get());
            }
        }
        r.setCategories(lastCats);
        r.setIngredients(lastIngs);
        r.setImage(true);
        r.setUsername(userSession.getLoggedUser());
        recipesRepository.save(r);
        imageService.saveImage("recipes", r.getId(), imageFile);
        int count = 1;
        if(allImages != null){
            for(MultipartFile mpf : allImages){
                if(mpf != null && !mpf.isEmpty()){
                    System.out.println("Paso " + count + " guardado como: "+ mpf);
                    imageService.saveImage("recipes/steps/"+r.getId(), count, mpf);
                    count++;
                }
            }
        }
        // Steps selector //
        stepsRepository.save(new Step(r, 1, firstStepString));
        System.out.println("Imagen unica: " + imageFile.toString());
        // System.out.println("Las imagenes: " + allImages[0].toString());
        if(stepsString != null){
            List<String> listOfSteps = Arrays.asList(stepsString.split("ab#12#45-3,"));
            for(String steps : listOfSteps){
                if(steps != null){
                    stepsRepository.save(new Step(r, i, steps));
                    i++;
                }
            }
        }
        //SHOW ELEMENTS
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        model.addAttribute("size", recipes.size());
        List<Recipe> subRecipe = recipes.subList(0, 3);
        model.addAttribute("subRecipe",subRecipe);

        List<Comment> comments = commentsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_comments", comments.size());

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