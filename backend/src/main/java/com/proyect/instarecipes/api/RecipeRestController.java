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
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private UsersRepository usersRepository;

    @JsonView(RecipeRestController.SimpleRecipe.class)
    @GetMapping("/")
    public ResponseEntity<Recipe> getRecipe(@RequestParam Long id_recipe){
        if (id_recipe != null){
            return new ResponseEntity<>(recipeService.getRecipe(id_recipe), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // get recipe steps
    @JsonView(RecipeRestController.RecipeSteps.class)
    @GetMapping("/steps")
    public ResponseEntity<List<Step>> getSteps(@RequestParam Long id_recipe){
        if (id_recipe != null){
            return new ResponseEntity<>(recipeService.getRecipeSteps(id_recipe), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(RecipeRestController.SimpleRecipe.class)
    @PostMapping("/recipeUnpressLike")
    public ResponseEntity<Recipe> unlikeRecipe(@RequestParam Long id_recipe, @RequestParam Long id_user){
        if (id_recipe != null){
            return new ResponseEntity<>(recipeService.pressRecipeUnlike(id_recipe, usersRepository.findById(id_user).get()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(RecipeRestController.SimpleRecipe.class)
    @PostMapping("/recipePressLike")
    public ResponseEntity<Recipe> likeRecipe(@RequestParam Long id_recipe, Long id_user){
        if (id_recipe != null){
            return new ResponseEntity<>(recipeService.pressRecipeLike(id_recipe, usersRepository.findById(id_user).get()),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* COMMENTS SECTION */

    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/commentPressLike")
    public ResponseEntity<Comment> likeComment(@RequestParam Long id_comment,@RequestParam Long id_user){
        if (id_comment != null){
            return new ResponseEntity<>(recipeService.likeComment(id_comment, usersRepository.findById(id_user).get()),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/commentUnpressLike")
    public ResponseEntity<Comment> unlikeComment(@RequestParam Long id_comment,@RequestParam Long id_user){
        if (id_comment != null){
            return new ResponseEntity<>(recipeService.unlikeComment(id_comment, usersRepository.findById(id_user).get()),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(RecipeRestController.CommentsRecipe.class)
    @GetMapping("/comments/")
    public ResponseEntity<List<Comment>> getComments(@RequestParam Long id_recipe){
        Optional<Recipe> recipe = recipesRepository.findById(id_recipe);
        if (recipe != null){
            return new ResponseEntity<>(commentsRepository.findAllByRecipe(recipe.get()), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/comments/")
    public ResponseEntity<Comment> setComments(@RequestParam(required = false) Long id_recipe, @RequestParam(required = false) String username,
    @RequestParam(required = false) String content, @RequestParam(required = false) Long parentComment){
        if (content != null){
            return new ResponseEntity<>(recipeService.postComment(id_recipe, content, parentComment, username), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}