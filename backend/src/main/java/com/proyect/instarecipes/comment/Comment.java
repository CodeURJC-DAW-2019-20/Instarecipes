package com.proyect.instarecipes.comment;

import java.sql.Date;
import java.time.Year;

public class Comment{
    private int id_comment;
    private int id_recipe;
    private String username;
    private String datetime;
    private String content;
    private int likes;
    private String parentId;

    public Comment(){
        this.id_comment=9;
    }

    public Comment(int id_recipe, String username,String datetime, String content,int likes,String parentId){
        this.id_comment=9;
        this.id_recipe=id_recipe;
        this.username=username;
        this.datetime=datetime;
        this.content=content;
        this.likes=likes;
        this.parentId=parentId;
    }

