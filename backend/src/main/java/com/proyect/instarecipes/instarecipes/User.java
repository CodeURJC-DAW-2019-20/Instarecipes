package com.proyect.instarecipes.instarecipes;

import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class User{
	
	private AtomicInteger id;
	private String username;
	private String email;
	private String password;
	private String name;
	private String surname;
	private int N_recipes;
	private int N_follows;
	private int N_followers;
	private Image background;
	private Image avatar;
	private ArrayList<String> allergens;
}