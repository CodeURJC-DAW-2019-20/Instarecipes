package com.proyect.instarecipes.cookingStyle;

import java.awt.Image;

public class CookStyle{
    private String name_cookstyle;
    private String name_category;
    private String id_ingredients;

    public CookStyle(String name_cookstyle){
        this.name_cookstyle=name_cookstyle;
    }

    public CookStyle( String name_category, String id_ingredients){
        this.name_cookstyle=name_cookstyle;
        this.name_category=name_category;
        this.id_ingredients=id_ingredients;
    }

    public String getname_cookstyle() {
        return name_cookstyle;
    }

    public void setname_cookstyle(String name_cookstyle) {
        this.name_cookstyle = name_cookstyle;
    }


    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public String getid_ingredients() {
        return id_ingredients;
    }

    public void setid_ingredients(String id_ingredients) {
        this.id_ingredients = id_ingredients;
    }

}