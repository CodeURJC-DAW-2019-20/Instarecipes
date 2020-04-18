import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, ViewChildren } from '@angular/core';
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
export class FilterRecipeComponent implements OnInit, AfterViewInit{
    private newAttribute: any = {};
    private fieldArray: Array<any> = [];
    allergens: Allergen [];
    ingredients: Ingredient [];
    cookingStyles: CookingStyle [];
    categories: Category [];
    filteredSearchDTO;
    ingString: string = "";

    @ViewChild('ingredientsSSearch') ingredientsString: ElementRef;
    @ViewChild('ingredientsSList') ingList: ElementRef;
    @ViewChild('categoriesSSearch') categoriesString: ElementRef;
    @ViewChild('categoriesSList') catList: ElementRef;
    @ViewChild('cookStylesSSearch') cookStyString: ElementRef;
    @ViewChild('cookStylesSList') cksList: ElementRef;
    @ViewChild('allergensSSearch') allergensString: ElementRef;
    @ViewChild('allergensSList') allgList: ElementRef;

    @ViewChild('selectedOption') selectedOption: ElementRef;
    //@ViewChildren('selectedOption') selectedOption: ElementRef;

  constructor(
    private profileService: ProfileService,
    ) {
      this.filteredSearchDTO = { ingredients: [], categories: [], cookingStyles: [], allergens: [] }
    }

  ngAfterViewInit() {
   // console.log("afterinit");
   // console.log(this.ingredientsString.nativeElement.value);
   import('../../../../assets/js/filter_search_btn.js');
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
        this.allergens = allergens;
        });
  }

  getIngredients(){
    this.profileService.getAllIngredients().subscribe(
      ingredients => {
        this.ingredients = ingredients;
      //  this.ingredients.forEach(element => this.ingString = this.ingString + element.ingredient + ",");
       // this.ingList.nativeElement.setAttribute("value",this.ingString);
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
    let ings = this.selectedOption.nativeElement.querySelectorAll("tr td:first-child select");
    console.log(ings.length);

    ings.forEach(element => {
      this.filteredSearchDTO.ingredients.push(element.value);
      console.log(element.value);
    });

    console.log(this.filteredSearchDTO);

  }

  seeJSON(data: any) {
   // console.log(this.selectedOption.nativeElement.value);
   // console.log(data);
    //this.getIngredients();
    console.log(this.filteredSearchDTO);
  }
}
