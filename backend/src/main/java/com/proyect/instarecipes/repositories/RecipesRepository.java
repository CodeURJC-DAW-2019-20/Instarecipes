package com.proyect.instarecipes.repositories;

import java.util.ArrayList;
import java.util.List;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUsernameId(Long username);

    @Query("SELECT username FROM Recipe r WHERE r.id = :id_recipe") 
	User findUsernameByRecipeId(Long id_recipe);

    @Query("SELECT r FROM Recipe r WHERE r.id = :id_recipe")
    Recipe findRecipeById(Long id_recipe);
    
    @Query("SELECT r FROM Recipe r ORDER BY r.id DESC")
    Page<Recipe> findAllRecipes(Pageable page); 

    @Query("SELECT r.username.username, SUM(r.likes) AS total FROM Recipe r GROUP BY r.username.username ORDER BY total DESC")
    Page<Recipe> findTopTen(Pageable page);
    
    
    @Query("SELECT DISTINCT r FROM Recipe r "
    + "LEFT OUTER JOIN r.ingredients ing "
    + "LEFT OUTER JOIN r.categories cat "
    + "LEFT OUTER JOIN r.cookingStyles cook "
    + "LEFT OUTER JOIN r.allergens ale "
    + "WHERE ing.ingredient IN :ingredients "
    + "OR cat.category IN :categories "
    + "OR cook.cookingStyle IN :cookingStyles "
    + "OR ale.allergen IN :allergens")
    List<Recipe> findFilteredSearch(ArrayList<String> ingredients, ArrayList<String> categories,
                                    ArrayList<String> cookingStyles, ArrayList<String> allergens);
    
    @Query("SELECT r FROM Recipe r ORDER BY r.likes DESC")
    List<Recipe> FindByLikes();

    @Query("SELECT DISTINCT r FROM Recipe r "
     +"WHERE r.username.id IN :fList OR r.username.id = :id_user ORDER BY r.id DESC")
	Page<Recipe> findAllRecipesByFollowing(@Param("fList") ArrayList<Long> fList,@Param("id_user") Long id_user, Pageable page);

  //  @Query("SELECT r FROM Recipe r WHERE title LIKE "%word%"")
  //  List<Recipe> findByTitle(String word);
}