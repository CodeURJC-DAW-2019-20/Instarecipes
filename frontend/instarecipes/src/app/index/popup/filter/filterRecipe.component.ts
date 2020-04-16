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
    @ViewChild('categoriesSSearch') categoriesString: ElementRef;
    @ViewChild('categoriesSList') catList: ElementRef;
    @ViewChild('cookStylesSSearch') cookStyString: ElementRef;
    @ViewChild('cookStylesSList') cksList: ElementRef;
    @ViewChild('allergensSSearch') allergensString: ElementRef;
    @ViewChild('allergensSList') allgList: ElementRef;

  constructor(
    private profileService: ProfileService,
    ) {
      this.filteredSearchDTO = { ingredients: [], categories: [], cookingStyles: [], allergens: [] }
    }

  ngOnInit() {
    this.getAllergens();
    this.getIngredients();
    this.getCookingStyles();
    this.getCategories();
    import('../../../../assets/js/filter_search_btn.js');
  }

  getAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => {
        this.allergens = allergens;
        });
  }

  getIngredients(){
    this.profileService.getAllIngredients().subscribe(
      ingredients => {
        this.ingredients = ingredients;
        });
  }

  getCategories(){
    this.profileService.getAllCategories().subscribe(
      categories => {
        this.categories = categories;
        });
  }

  getCookingStyles(){
    this.profileService.getAllCookingStyles().subscribe(
      cookingStyles => {
        this.cookingStyles = cookingStyles;
        });
  }

  postFilterRecipe() {
    this.filteredSearchDTO.ingredients.push(this.ingredientsString.nativeElement.value);
    console.log(this.filteredSearchDTO);
  }

  seeJSON() {
    console.log(this.filteredSearchDTO);
  }
}
