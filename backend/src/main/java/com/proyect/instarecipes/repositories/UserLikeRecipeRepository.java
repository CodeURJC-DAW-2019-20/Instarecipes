package com.proyect.instarecipes.repositories;

import java.util.List;
import java.util.Optional;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.UserLikeRecipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRecipeRepository extends JpaRepository<UserLikeRecipe, Long> {
    // List<Recipe> findByUsernameId(Long username);
    
    //  @Query("SELECT l.id, l.username, l.recipe, l.checked FROM UserLikeRecipe l ORDER BY l.id DESC")
    //  List<UserLikeRecipe> findAllLikes(); 

    // @Query("SELECT l FROM UserLikeRecipe l WHERE l.recipe= :r")
    // Optional<UserLikeRecipe> findByRecipe(Recipe r);

    //List<UserLikeRecipe> findAll();
}