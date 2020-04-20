package com.proyect.instarecipes.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Comment{
    public interface RecipeView{}
    public interface UserLikeView{}

    @JsonView(RecipeView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(RecipeView.class)
    @ManyToOne
    private User userComment;
    
    @JsonView(RecipeView.class)
    @Column(nullable = false,length = 500)
    private String content;

    @JsonView(RecipeView.class)
    private boolean hasSubcomments;
    
    @JsonView({RecipeView.class, UserLikeView.class})
    private boolean isSubcomment;

    @JsonView(RecipeView.class)
    @OneToMany
    private Set<Comment> subComments;

    @ManyToOne
    private Recipe recipe;

    @ManyToMany
    private Set<User> usersLiked;

    @JsonView(RecipeView.class)
    private int likes; //THESE IS ONLY AN AUX
    @JsonView(RecipeView.class)
    private boolean liked;
    
    public Comment() {}

    public Comment(User userComment, String content, Set<Comment> subComments,
     Recipe recipe, boolean hasSubcomments, boolean isSubcomment, Set<User> usersLiked) {
        this.userComment = userComment;
        this.content = content;
        this.subComments = subComments;
        this.recipe = recipe;
        this.hasSubcomments = hasSubcomments;
        this.isSubcomment = isSubcomment;
        this.usersLiked = usersLiked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserComment() {
        return userComment;
    }

    public void setUserComment(User userComment) {
        this.userComment = userComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Set<Comment> getSubComments() {
        return subComments;
    }

    public void setSubComments(Set<Comment> subComments) {
        this.subComments = subComments;
    }

    public boolean isHasSubcomments() {
        return hasSubcomments;
    }

    public void setHasSubcomments(boolean hasSubcomments) {
        this.hasSubcomments = hasSubcomments;
    }

    public boolean isSubcomment() {
        return isSubcomment;
    }

    public void setSubcomment(boolean isSubcomment) {
        this.isSubcomment = isSubcomment;
    }

    public Set<User> getUsersLiked() {
        return usersLiked;
    }

    public void setUsersLiked(Set<User> usersLiked) {
        this.usersLiked = usersLiked;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes += likes;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
    
    public void removeLikeUser(User u){
        usersLiked.remove(u);
    }

	public void addLikeUser(User u) {
        usersLiked.add(u);
	}
}