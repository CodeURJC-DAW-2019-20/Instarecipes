package com.proyect.instarecipes.api;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public interface SimpleRecipe extends Recipe.RecipeView, 
    Recipe.RecipeBasic, Recipe.RecipePlus, Recipe.RecipeExtra, Ingredient.Item, Category.Item, User.Username, User.NameSurname{}
    public interface CommentsRecipe extends Comment.RecipeView, User.NameSurname, User.Username, Recipe.RecipeView{}
    public interface RecipeSteps extends Step.StepsView{}

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserSession userSession;

    @JsonView(RecipeRestController.SimpleRecipe.class)
    @GetMapping("/{id_recipe}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id_recipe){
        if (id_recipe != null){
            return new ResponseEntity<>(recipeService.getRecipe(id_recipe), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // get recipe steps
    @JsonView(RecipeRestController.RecipeSteps.class)
    @GetMapping("/{id_recipe}/steps")
    public ResponseEntity<List<Step>> getSteps(@PathVariable Long id_recipe){
        if (id_recipe != null){
            return new ResponseEntity<>(recipeService.getRecipeSteps(id_recipe), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(RecipeRestController.SimpleRecipe.class)
    @PostMapping("/{id_recipe}/recipeUnpressLike")
    public ResponseEntity<Recipe> unlikeRecipe(@PathVariable Long id_recipe){
        if(userSession.isLoggedUser()){
            if (id_recipe != null){
                return new ResponseEntity<>(recipeService.pressRecipeUnlike(id_recipe,userSession.getLoggedUser()), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

    @JsonView(RecipeRestController.SimpleRecipe.class)
    @PostMapping("/{id_recipe}/recipePressLike")
    public ResponseEntity<Recipe> likeRecipe(@PathVariable Long id_recipe){
        if(userSession.isLoggedUser()){
            if (id_recipe != null){
                return new ResponseEntity<>(recipeService.pressRecipeLike(id_recipe,userSession.getLoggedUser()),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

    /* COMMENTS SECTION */

    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/comments/{id_comment}/PressLike")
    public ResponseEntity<Comment> likeComment(@PathVariable Long id_comment){
        if(userSession.isLoggedUser()){
            if (id_comment != null){
                return new ResponseEntity<>(recipeService.likeComment(id_comment,userSession.getLoggedUser()),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }
    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/comments/{id_comment}/UnpressLike")
    public ResponseEntity<Comment> unlikeComment(@PathVariable Long id_comment){
        if(userSession.isLoggedUser()){
            if (id_comment != null){
                return new ResponseEntity<>(recipeService.unlikeComment(id_comment,userSession.getLoggedUser()),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

    @JsonView(RecipeRestController.CommentsRecipe.class)
    @GetMapping("/{id_recipe}/comments/")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long id_recipe){
        Optional<Recipe> recipe = recipesRepository.findById(id_recipe);
        if (recipe != null){
            return new ResponseEntity<>(commentsRepository.findAllByRecipe(recipe.get()), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/{id_recipe}/comments/")
    public ResponseEntity<Comment> setComments(@PathVariable(required = false) Long id_recipe, @RequestBody CommentDto commentdto){
        if(userSession.isLoggedUser()){
            if (commentdto.getContent() != null){
                return new ResponseEntity<>(recipeService.postComment(id_recipe, commentdto.getContent(), commentdto.getParentComment(), userSession.getLoggedUser()), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

}