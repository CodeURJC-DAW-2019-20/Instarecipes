package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService{

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private UserSession userSession;
    
    public List<Comment> getRecipeComments(Recipe recipe){
        return commentsRepository.findAllByRecipeOrderByLikes(recipe);
    }

    public Recipe getRecipe(Long id_recipe){
        return recipesRepository.findRecipeById(id_recipe);
    }

    public User getUserRecipe(Long id_recipe){
        return recipesRepository.findUsernameByRecipeId(id_recipe);
    }

    public List<Integer> getRecipeLikesAndPublications(Long id){
        List<Recipe> recipes = recipesRepository.findByUsernameId(id);
        int n_likes = 0;
        int n_publications;
        for (n_publications = 0; n_publications < recipes.size(); n_publications++) {
            n_likes = n_likes + recipes.get(n_publications).getLikes();
        }
        List<Integer> array = new ArrayList<>();
        array.add(n_publications); // 1st position -> pubs
        array.add(n_likes); // 2st position -> likes
        return array;
    }

    public List<Step> getRecipeSteps(Long id_recipe){
        Optional<Recipe> recipe = recipesRepository.findById(id_recipe);
        return stepsRepository.findAllByRecipe(recipe.get());
    }

    public Recipe pressRecipeUnlike(Long id_recipe, User user){
        Recipe recipe = recipesRepository.findRecipeById(id_recipe);
        Set<User> recipeLikes = recipe.getLikesUsers();
        for(User u : recipeLikes){
            if(u.getId() == user.getId()){
                recipe.removeUser(u);           
                recipesRepository.flush();
                break;
            }
        }
        return recipe;
    }

    public Recipe pressRecipeLike(Long id_recipe, User user){
        Recipe recipe = recipesRepository.findRecipeById(id_recipe);
        Set<User> recipeLikes = recipe.getLikesUsers();
        boolean check=false;
        for(User u : recipeLikes){
            if(u.getId()==user.getId()){              
                check= true;
                System.out.println("Hola valgo id: " + u.getId());
                break;
            }
        }
        if(!check){         
            recipe.addUser(user);
            recipesRepository.flush();
        }
        return recipe;
    }
    public List<Comment> likedcomment(Long id_recipe,User user){
        Recipe recipe = recipesRepository.findRecipeById(id_recipe);
        List<Comment> comments = commentsRepository.findAllByRecipeOrderByLikes(recipe);
        User me = userSession.getLoggedUser();
       
        for(int i = 0; i< comments.size();i++){
            Comment comment = comments.get(i);
            List<User> users = comments.get(i).getUsersLiked().stream().collect(Collectors.toList());
            System.out.println("this is the comment" +comment.getContent());
            
            for(int j = 0; j< users.size();j++){
                System.out.println("estoy aqui? "+ users.get(j).getId());
                System.out.println("este soy yo "+ me.getId());
                if( users.get(j).getId() == me.getId()){
                    comment.setLiked(true);
                    commentsRepository.flush();
                } else if( comment.isLiked()){ }
            }        
        }
        return comments;
    }

    public Comment likeComment(Long id_comment, User user){
        Optional<Comment> comment = commentsRepository.findById(id_comment);
        Set<User> newList = comment.get().getUsersLiked();
        boolean aux = true;
        for(User u : newList){
            if(u.getId() == user.getId()){
                aux = false;
                break;
            }
        }
        if(aux){
            comment.get().setLiked(true);
            comment.get().setLikes(1);
            comment.get().addLikeUser(user);
            commentsRepository.flush();
        }
        return comment.get();
    }

    public Comment unlikeComment(Long id_comment, User user){
        Optional<Comment> comment = commentsRepository.findById(id_comment);
        Set<User> newList = comment.get().getUsersLiked();
        for(User u : newList){
            if(u.getId() == user.getId()){
                comment.get().setLiked(false);
                comment.get().setLikes(-1);
                comment.get().removeLikeUser(u);
                commentsRepository.flush();
                break;
            }
        }
        return comment.get();
    }
    
    public Comment postComment(Long id, String content, Long parentComment, User user){
        Comment comment = null;
        Recipe r = recipesRepository.findRecipeById(id);
        if(parentComment != null){ //Subcomment
            Optional<Comment> pComment = commentsRepository.findById(parentComment);
            comment = new Comment(user, content, null, r, false, true, null);//get the comment
            if(content != ""){
                commentsRepository.save(comment);
                Set<Comment> ejem = new HashSet<>();
                ejem = pComment.get().getSubComments();
                ejem.add(comment);
                if(!pComment.get().isSubcomment()){
                    commentsRepository.setParentHasComment(true, parentComment);
                }else{
                    commentsRepository.setParentHasComment(false, parentComment);
                }
            }
        }else{ //Normal comment
            comment = new Comment(user, content, null, r, false, false, null);
            if(content != ""){
                commentsRepository.save(comment);
            }
        }
        commentsRepository.flush();
        return comment;
    }

}