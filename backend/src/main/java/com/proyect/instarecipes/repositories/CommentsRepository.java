package com.proyect.instarecipes.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    //The querys has to be so strict, every single letter should go as same as the classes, like capital letters
    @Query("SELECT COUNT(*) FROM Comment c WHERE c.recipe= :id_recipe") 
    int countByRecipeId(Recipe id_recipe);


    List<Comment> findAllByRecipe(Recipe recipe);

    @Query("SELECT c FROM Comment c WHERE c.recipe=:recipe ORDER BY c.likes DESC")
    List<Comment> findAllByRecipeOrderByLikes(Recipe recipe);

    @Query("SELECT c FROM Comment c WHERE c.id = :cid")
    Set<Comment> findSubCommentsById(Long cid);
    
    @Transactional
    @Modifying
    @Query("UPDATE Comment c SET c.hasSubcomments = :now_hasComment WHERE c.id = :cid")
    void setParentHasComment(@Param("now_hasComment") boolean now_hasComment, @Param("cid") Long cid);

    @Query("SELECT cl.usersLiked FROM Comment cl WHERE cl.id = :id")
	Set<User> findLikeUsersList(Long id);

    @Query("UPDATE Comment c SET c.usersLiked = :new_list WHERE c.id = :id_comment")
	void updateLikesUsersList(Long id_comment, Set<User> new_list);

}