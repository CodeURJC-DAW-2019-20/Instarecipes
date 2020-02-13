package com.proyect.instarecipes.step;

import java.awt.Image;

public class Step{
    private int id_step;
    private int id_recipe;
    private int number;
    private String Content;
    private Image photo;

    public Step(){
        id_step=9;
    }
    
    public Step(int id_recipe,int number, String Contect, Image photo){
        this.id_recipe=id_recipe;
        this.number=number;
        this.Content=Contect;
        this.photo=photo;
    }

    public int getId_step() {
        return id_step;
    }

    public void setId_step(int id_step) {
        this.id_step = id_step;
    }

    public int getId_recipe() {
        return id_recipe;
    }

    public void setId_recipe(int id_recipe) {
        this.id_recipe = id_recipe;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    
}

