package com.proyect.instarecipes.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.RecipeService;
import com.proyect.instarecipes.views.DTO.CommentDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController{

    public interface SimpleRecipe extends Recipe.RecipeView, Recipe.IDRecipe,
    Recipe.RecipeBasic, Recipe.RecipePlus, Recipe.RecipeExtra, Ingredient.Item, Category.Item, User.Username, User.NameSurname, Recipe.Rankinglikes{}
    public interface CommentsRecipe extends Comment.RecipeView, User.NameSurname, User.Username, Recipe.RecipeView{}
    public interface RecipeSteps extends Step.StepsView{}
    public interface Main extends User.Username, Recipe.RecipeBasic, Recipe.RecipePlus, Recipe.IDRecipe, User.IDUser{}
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserSession userSession;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/last")
	public ResponseEntity<Integer> getLastRecipeId() {
		Integer allRecipes = recipesRepository.findAll().size();
		return new ResponseEntity<>(allRecipes, HttpStatus.OK);
    }
    
    // AJAX PAGEABLE RECIPES
    @JsonView(RecipeRestController.Main.class)
    @GetMapping("/")
    public List<Recipe> getRecipes(@RequestParam int page, @RequestParam int size){
        Page<Recipe> recipes = null;
        if(userSession.isLoggedUser()){
            User user = userSession.getLoggedUser();
            Set<User> fList = usersRepository.findFollowingSet(user.getUsername());
            ArrayList<Long> list1 = new ArrayList<>();
            for(User u : fList){
                list1.add(u.getId());
            }
            recipes = recipesRepository.findAllRecipesByFollowing(list1, user.getId(),PageRequest.of(page,size));
        }else{
            recipes = recipesRepository.findAllRecipes(PageRequest.of(page,size));
        }
        List<Recipe> recipeList = (List<Recipe>)recipes.getContent();
        return recipeList;
    }
    //SHOW THE IMAGE OF ANOTHER USER
    @GetMapping(value = "/{id}/avatar",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) throws IOException {
        User user = usersRepository.findById(id).get();
        if(user.getImage().length > 0){
            byte[] image = user.getImage();
            return new ResponseEntity<>(image, HttpStatus.OK);
        }else{
            File file = new File("temp/avatars/image-"+user.getId()+".jpg");
            return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
        }
    }

    //SHOW ONE RECIPE
    @JsonView(RecipeRestController.SimpleRecipe.class)
    @GetMapping("/{id_recipe}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id_recipe){
        if (id_recipe != null){
            return new ResponseEntity<>(recipeService.getRecipe(id_recipe), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // SHOW LIST OF STEPS OF ONE RECIPE
    @JsonView(RecipeRestController.RecipeSteps.class)
    @GetMapping("/{id_recipe}/steps")
    public ResponseEntity<List<Step>> getSteps(@PathVariable Long id_recipe){
        if (id_recipe != null){
            return new ResponseEntity<>(recipeService.getRecipeSteps(id_recipe), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PRESS UNLIKE TO A RECIPE
    @JsonView(RecipeRestController.SimpleRecipe.class)
    @PostMapping("/{id_recipe}/recipeUnpressLike")
    public ResponseEntity<Recipe> unlikeRecipe(@PathVariable Long id_recipe){
        if(userSession.isLoggedUser()){
            if (id_recipe != null){
                User u = usersRepository.findByUsername(userSession.getLoggedUser().getUsername());
                return new ResponseEntity<>(recipeService.pressRecipeUnlike(id_recipe,u), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

    // PRESS LIKE TO A RECIPE
    @JsonView(RecipeRestController.SimpleRecipe.class)
    @PostMapping("/{id_recipe}/recipePressLike")
    public ResponseEntity<Recipe> likeRecipe(@PathVariable Long id_recipe){
        if(userSession.isLoggedUser()){
            if (id_recipe != null){
                User u = usersRepository.findByUsername(userSession.getLoggedUser().getUsername());
                return new ResponseEntity<>(recipeService.pressRecipeLike(id_recipe,u),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

    /* COMMENTS SECTION */

    // POST A COMMENT IN ONE RECIPE
    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/{id_recipe}/comments/")
    public ResponseEntity<Comment> setComments(@PathVariable Long id_recipe, @RequestBody CommentDTO commentdto){
        if(userSession.isLoggedUser()){
            User u = usersRepository.findByUsername(userSession.getLoggedUser().getUsername());
            if (commentdto.getContent() != null){
                return new ResponseEntity<>(recipeService.postComment(id_recipe, commentdto.getContent(), commentdto.getParentComment(),u), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

    // SHOW LIST OF COMMENTS OF ONE RECIPE
    @JsonView(RecipeRestController.CommentsRecipe.class)
    @GetMapping("/{id_recipe}/comments/")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long id_recipe){
        Optional<Recipe> recipe = recipesRepository.findById(id_recipe);
        if(userSession.isLoggedUser()){
            User user = userSession.getLoggedUser();
            List<Comment> comments = recipeService.likedcomment(id_recipe, user);
            if (recipe != null){
                return new ResponseEntity<>(comments, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        if (recipe != null){
            return new ResponseEntity<>(commentsRepository.findAllByRecipe(recipe.get()), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PRESS LIKE TO ONE COMMENT 
    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/{id_recipe}/comments/{id_comment}/commentPressLike")
    public ResponseEntity<Comment> likeComment(@PathVariable Long id_comment){
        if(userSession.isLoggedUser()){
            if (id_comment != null){
                User u = usersRepository.findByUsername(userSession.getLoggedUser().getUsername());
                return new ResponseEntity<>(recipeService.likeComment(id_comment,u),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }
    
    // PRESS UNLIKE TO ONE COMMENT
    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/{id_recipe}/comments/{id_comment}/commentUnpressLike")
    public ResponseEntity<Comment> unlikeComment(@PathVariable Long id_comment){
        if(userSession.isLoggedUser()){
            if (id_comment != null){
                User u = usersRepository.findByUsername(userSession.getLoggedUser().getUsername());
                return new ResponseEntity<>(recipeService.unlikeComment(id_comment,u),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }
    
}