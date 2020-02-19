package com.proyect.instarecipes.repositories;

import java.util.List;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
    //The querys has to be so strict, every single letter should go as same as the classes, like capital letters
    @Query("SELECT COUNT(*) FROM Comment c WHERE c.recipe= :id_recipe") 
    int countByRecipeId(Recipe id_recipe);

	List<Comment> findAllByRecipe(Recipe recipe);
}