package com.proyect.instarecipes.repositories;

import java.util.List;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StepsRepository extends JpaRepository<Step, Long> {
    @Query("SELECT s FROM Step s WHERE s.recipe = :recipe")
	List<Step> findAllByRecipe(Recipe recipe);

    @Query("SELECT s FROM Step s WHERE s.recipe = :r ORDER BY s.number ASC")
	List<Step> findAllByRecipeOrderNumberStep(Long r);

    @Query("SELECT s FROM Step s WHERE s.number = :n_step AND s.recipe.id = :id_recipe")
	Step findByRecipeIdAndNumber(Long id_recipe, int n_step);
}