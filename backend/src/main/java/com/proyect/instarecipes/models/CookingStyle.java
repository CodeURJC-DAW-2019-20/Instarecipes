package com.proyect.instarecipes.models;

public class CookingStyle{
    private String name_cookingstyle;
    private String name_category;
    private String id_ingredients;

    public CookingStyle(String name_cookingstyle){
        this.name_cookingstyle=name_cookingstyle;
    }

    public CookingStyle(String name_cookingstyle, String name_category, String id_ingredients){
        this.name_cookingstyle=name_cookingstyle;
        this.name_category=name_category;
        this.id_ingredients=id_ingredients;
    }

    public String getname_cookingstyle() {
        return name_cookingstyle;
    }

    public void setname_cookingstyle(String name_cookingstyle) {
        this.name_cookingstyle = name_cookingstyle;
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