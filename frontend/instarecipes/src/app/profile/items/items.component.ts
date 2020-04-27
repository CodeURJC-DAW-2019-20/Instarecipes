import { Component, OnInit } from '@angular/core';
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

  loadAPI: any;

  constructor(private profileService: ProfileService){
  }

  ngOnInit(){
    this.loadItems();
  }

  loadItems() {
    this.getAllIngredients();
    this.getAllCategories();
    this.getAllCookingStyles();
    this.loadAPI = new Promise(resolve => {
      console.log("resolving promise...");
      this.loadScript();
    });
  }

  public loadScript() {
    console.log("preparing to load...");
    let node = document.createElement("script");
    node.src = 'assets/js/admin_profile.js';
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
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
