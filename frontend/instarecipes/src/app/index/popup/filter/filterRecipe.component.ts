import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { ProfileService } from 'src/app/services/profile.service';
import { registerLocaleData } from '@angular/common';
import { CookingStyle } from 'src/app/Interfaces/cookingStyle.model';
import { Category } from 'src/app/Interfaces/category.model';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'popup-filter',
  templateUrl: './filterRecipe.component.html',
  styleUrls: ['./filterRecipe.component.css']
})
export class FilterRecipeComponent implements OnInit{
    private newAttribute: any = {};
    private fieldArray: Array<any> = [];
    allergens: Allergen [];
    ingredients: Ingredient [];
    cookingStyles: CookingStyle [];
    categories: Category [];
    filteredSearchDTO;

    @ViewChild('ingredientsSSearch') ingredientsString: ElementRef;
    @ViewChild('ingredientsSList') ingList: ElementRef;

  constructor(
    private profileService: ProfileService,
    ) {
      this.filteredSearchDTO = { ingredients: "", categories: "", cookingStyles: "", allergens: "" }
    }

  ngOnInit() {
    this.getAllergens();
    this.getIngredients();
    this.getCookingStyles();
    this.getCategories();
  }

  getAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => {
        this.allergens = allergens as Allergen[];
        });
  }

  getIngredients(){
    this.profileService.getAllIngredients().subscribe(
      ingredients => {
        this.ingredients = ingredients as Ingredient[];
        });
  }

  getCategories(){
    this.profileService.getAllCategories().subscribe(
      categories => {
        this.categories = categories as Category[];
        });
  }

  getCookingStyles(){
    this.profileService.getAllCookingStyles().subscribe(
      cookingStyles => {
        this.cookingStyles = cookingStyles as CookingStyle[];
        });
  }

  insRowIngredient() {
    //var table = document.getElementById("IngredientsTable");
    // var totalRows = table.rows.length;
    // var new_row = table.rows[0].cloneNode(true);
    // var inp1 = new_row.cells[0].getElementsByTagName('input')[0]; //ingredient

    // inp1.id += totalRows;
    // inp1.value = ''; //new slot empty!

    // table.appendChild(new_row);
    this.fieldArray.push(this.newAttribute)
    this.newAttribute = {};
  }

  postFilterRecipe(data: NgForm) {

  }

  seeJSON() {
    console.log(this.ingredientsString);
    console.log(this.ingList);
    console.log(this.filteredSearchDTO);
  }
}
