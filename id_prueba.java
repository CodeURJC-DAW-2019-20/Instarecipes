package com.proyect.instarecipes.recipe;

public class id_prueba{
    private String id_recipe;
    private String username;
    private int id_ingredients;
    private String name_categories;
    private String name_cookingStyle;
    private String title;
    private String description;
    private String duration;
    private String dificulty;
    private String steps;
    private Image galery;
//",String description,String duration,String dificulty,String steps"
    public id_prueba(){
        this.id_recipe=1;
    }
    public void create_ids(String username, int id_ingredients,String name_categories,
     String name_cookingStyle, 
    String title){

        this.id_recipe = username+id_ingredients+name_categories+name_cookingStyle+title;
        this.id_recipe = id_recipe.applymap(String);
        print(this.id_recipe);
    //df_in[id_col] = id_recipe.apply(lambda x: ''.join(x.values), axis=1)
    //df_in[id_col] = df_in[id_col].apply(lambda x: md5(bytes(x, 'utf-8')).hexdigest())

    }
    
}