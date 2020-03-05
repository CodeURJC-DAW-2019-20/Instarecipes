package com.proyect.instarecipes.api;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController{

    public interface SimpleRecipe extends Recipe.RecipeView, 
    Recipe.RecipeBasic, Recipe.RecipePlus, Recipe.RecipeExtra, Ingredient.Item, Category.Item, User.Username, User.NameSurname{}
    public interface CommentsRecipe extends Comment.RecipeView, User.NameSurname, User.Username{}

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @JsonView(RecipeRestController.SimpleRecipe.class)
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id){
        Optional<Recipe> recipe = recipesRepository.findById(id);
        if (recipe.get() != null){
            return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(RecipeRestController.CommentsRecipe.class)
    @GetMapping("/comments/{id}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable long id){
        Optional<Recipe> recipe = recipesRepository.findById(id);
        List<Comment> commentsList = commentsRepository.findAllByRecipe(recipe.get());
        if (commentsList != null){
            return new ResponseEntity<>(commentsList, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(RecipeRestController.CommentsRecipe.class)
    @PostMapping("/")
    public ResponseEntity<Comment> setComments(@RequestParam(required = false) Long id, @RequestParam(required = false) String username,
    @RequestParam(required = false) String content, @RequestParam(required = false) Long parentComment){

        Comment comment = null;
        System.out.println("Username: " + username);
        User u = usersRepository.findByUsername(username);
        System.out.println("Id: " + u.getId());
        Recipe r = recipesRepository.findRecipeById(id);

        if(parentComment != null){ //Subcomment
            Optional<Comment> pComment = commentsRepository.findById(parentComment);
            comment = new Comment(u, content, null, r, false, true, null);//get the comment
            if(content != ""){
                commentsRepository.save(comment);
                Set<Comment> ejem = new HashSet<>();
                ejem = pComment.get().getSubComments();
                ejem.add(comment);
                if(!pComment.get().isSubcomment()){
                    commentsRepository.setParentHasComment(true, parentComment);
                }
                else{
                    commentsRepository.setParentHasComment(false, parentComment);
                }
            }
        }else{ //Normal comment
            comment = new Comment(u, content, null, r, false, false, null);
            if(content != ""){
                commentsRepository.save(comment);

            }
        }
        commentsRepository.flush();

        if (content != null){
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}