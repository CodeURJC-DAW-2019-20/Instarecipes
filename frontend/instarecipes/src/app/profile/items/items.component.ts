import { Component, OnInit } from '@angular/core';
import { RecipesService } from 'src/app/services/recipes.service';
import { ProfileService } from 'src/app/services/profile.service';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { Category } from 'src/app/Interfaces/category.model';
import { CookingStyle } from 'src/app/Interfaces/cookingStyle.model';

@Component({
  selector: 'items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {

  ingredients: Ingredient[] = [];
  categories: Category[] = [];
  cookingStyles: CookingStyle[] = [];

  constructor(private recipesService: RecipesService, private profileService: ProfileService){
  }

  ngOnInit(){
   this.loadItems();
  }

  loadItems() {
   this.getAllIngredients();
   this.getAllCategories();
   this.getAllCookingStyles();
   import("../../../assets/js/admin_profile.js");
  }

  getAllIngredients() {
    this.profileService.getAllIngredients().subscribe(
      ingredient => {
        this.ingredients = ingredient as Ingredient[];
      }
    )
  }

  getAllCategories() {
    this.profileService.getAllCategories().subscribe(
      category => {
        this.categories = category as Category[];
      }
    )
  }

  getAllCookingStyles() {
    this.profileService.getAllCookingStyles().subscribe(
      cookingStyle => {
        this.cookingStyles = cookingStyle as CookingStyle[];
      }
    )
  }
}
