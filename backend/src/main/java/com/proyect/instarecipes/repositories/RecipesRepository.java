package com.proyect.instarecipes.repositories;

import java.util.List;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUsernameId(Long username);

    @Query("SELECT username FROM Recipe r WHERE r.id = :id_recipe") 
	User findUsernameByRecipeId(Long id_recipe);

    @Query("SELECT r FROM Recipe r WHERE r.id = :id_recipe")
	Recipe findRecipeById(Long id_recipe);
}