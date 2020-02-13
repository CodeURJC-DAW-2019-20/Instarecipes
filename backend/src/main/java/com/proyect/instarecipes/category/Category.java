package com.proyect.instarecipes.category;

import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class Category{
	
	private String name_category;
    private String id_ingredients;
	
	public Category(String name_category){
        this.name_category=name_category;
    }

    public Category( String name_category, String id_ingredients){
        this.name_category=name_category;
        this.id_ingredients=id_ingredients;
    }

	
    
}