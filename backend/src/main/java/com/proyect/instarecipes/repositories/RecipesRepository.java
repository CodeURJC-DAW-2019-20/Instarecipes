package com.proyect.instarecipes.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUsernameId(Long username);

    @Query("SELECT username FROM Recipe r WHERE r.id = :id_recipe") 
	User findUsernameByRecipeId(Long id_recipe);

    @Query("SELECT r FROM Recipe r WHERE r.id = :id_recipe")
    Recipe findRecipeById(Long id_recipe);
    
    @Query("SELECT r.id, r.title, r.description, r.username.id, r.username.username, r.username.name, r.username.surname, r.likes FROM Recipe r")
    Page<Recipe> findAllRecipes(Pageable page);

    @Query("SELECT r.username.username, SUM(r.likes) AS total FROM Recipe r GROUP BY r.username.username ORDER BY total DESC")
	Page<Recipe> findTopTen(Pageable page);
    
    @Query("SELECT DISTINCT r FROM Recipe r "
    + "INNER JOIN r.ingredients ing INNER JOIN r.categories cat INNER JOIN r.allergens ale INNER JOIN r.cookingStyles cok WHERE "
    + "(ing.ingredient IN :ingredients) OR "
    + "(cat.category IN :categories) OR "
    + "(ale.allergen IN :allergens) OR "
    + "(cok.cookingStyle IN :cookingStyles)")
    List<Recipe> findFilteredSearch(ArrayList<String> ingredients, 
                                    ArrayList<String> categories, 
                                    ArrayList<String> allergens, 
                                    ArrayList<String> cookingStyles);
}