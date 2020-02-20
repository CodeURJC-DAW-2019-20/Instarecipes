package com.proyect.instarecipes.views;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;

public class GroupStaff{

    public GroupStaff() {}

    public Set<Ingredient> groupIngredients(Ingredient... ingredient){
        return new HashSet<>(Arrays.asList(ingredient));
    }
    public Set<Category> groupCategories(Category... category){
        return new HashSet<>(Arrays.asList(category));
    }
    public Set<CookingStyle> groupCookingStyles(CookingStyle... cookingStyle){
        return new HashSet<>(Arrays.asList(cookingStyle));
    }
    public Set<Step> groupSteps(Step... steps){
        return new HashSet<>(Arrays.asList(steps));
    }
    public Set<User> groupFollowing(User... users){
        return new HashSet<>(Arrays.asList(users));
    }
    public Set<User> groupFollowers(User... users){
        return new HashSet<>(Arrays.asList(users));
    }

	public Set<Comment> groupComments(Comment... comments) {
		return new HashSet<>(Arrays.asList(comments));
	}

	public Set<Allergen> groupAllergens(Allergen... allergens) {
		return new HashSet<>(Arrays.asList(allergens));
	}
}