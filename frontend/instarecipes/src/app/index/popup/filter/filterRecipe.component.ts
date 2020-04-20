import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { ProfileService } from 'src/app/services/profile.service';
import { CookingStyle } from 'src/app/Interfaces/cookingStyle.model';
import { Category } from 'src/app/Interfaces/category.model';
import { SearchService } from 'src/app/services/search.service.js';
import { Router } from '@angular/router';
import { Recipe } from 'src/app/Interfaces/recipe.model.js';

@Component({
  selector: 'popup-filter',
  templateUrl: './filterRecipe.component.html',
  styleUrls: ['./filterRecipe.component.css']
})
export class FilterRecipeComponent implements OnInit, AfterViewInit{

    allergens: Allergen [];
    ingredients: Ingredient [];
    cookingStyles: CookingStyle [];
    categories: Category [];
    filteredSearchDTO;
    filtersFinal;
    ingString: string = "";
    recipes: Recipe[] = [];

    loadAPI: any;

    @ViewChild('ingredientsSSearch') ingredientsString: ElementRef;
    @ViewChild('ingredientsSList') ingList: ElementRef;
    @ViewChild('categoriesSSearch') categoriesString: ElementRef;
    @ViewChild('categoriesSList') catList: ElementRef;
    @ViewChild('cookStylesSSearch') cookStyString: ElementRef;
    @ViewChild('cookStylesSList') cksList: ElementRef;
    @ViewChild('allergensSSearch') allergensString: ElementRef;
    @ViewChild('allergensSList') allgList: ElementRef;

    @ViewChild('selectedIng') selectedIng: ElementRef;
    @ViewChild('selectedCookS') selectedCookS: ElementRef;
    @ViewChild('selectedCat') selectedCat: ElementRef;
    @ViewChild('selectedAll') selectedAll: ElementRef;
    @ViewChild('closebutton') closebutton: ElementRef;

  constructor(private profileService: ProfileService, private searchService: SearchService,
              private router: Router) {
      this.filteredSearchDTO = { ingredients: [], categories: [], cookingStyles: [], allergens: [] },
      this.filtersFinal = { ingredients: "", categories: "", cookingStyles: "", allergens: "" }
  }

  ngAfterViewInit() {
    this.loadAPI = new Promise(resolve => {
      console.log("resolving promise...");
      this.loadScript();
    });
  }

  public loadScript() {
    console.log("preparing to load...");
    let node = document.createElement("script");
    node.src = 'assets/js/filter_search_btn.js';
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
  }

  ngOnInit() {
    this.getAllergens();
    this.getIngredients();
    this.getCookingStyles();
    this.getCategories();
  }

  getAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => this.allergens = allergens
    );
  }

  getIngredients(){
    this.profileService.getAllIngredients().subscribe(
      ingredients => this.ingredients = ingredients
    );
  }

  getCategories(){
    this.profileService.getAllCategories().subscribe(
      categories => this.categories = categories
    );
  }

  getCookingStyles(){
    this.profileService.getAllCookingStyles().subscribe(
      cookingStyles => this.cookingStyles = cookingStyles
    );
  }

  postFilterRecipe() {
    let ings = this.selectedIng.nativeElement.querySelectorAll("tr td:first-child select");
    ings.forEach(element => {
      this.filteredSearchDTO.ingredients.push(element.value);
    });

    let cookS = this.selectedCookS.nativeElement.querySelectorAll("tr td:first-child select");
    cookS.forEach(element => {
      this.filteredSearchDTO.cookingStyles.push(element.value);
    });

    let cat = this.selectedCat.nativeElement.querySelectorAll("tr td:first-child select");
    cat.forEach(element => {
      this.filteredSearchDTO.categories.push(element.value);
    });

    let allerg = this.selectedAll.nativeElement.querySelectorAll("tr td:first-child select");
    allerg.forEach(element => {
      this.filteredSearchDTO.allergens.push(element.value);
    });
    this.closebutton.nativeElement.click();

    this.filtersFinal.ingredients = this.filteredSearchDTO.ingredients.toString();
    this.filtersFinal.categories = this.filteredSearchDTO.categories.toString();
    this.filtersFinal.cookingStyles = this.filteredSearchDTO.cookingStyles.toString();
    this.filtersFinal.allergens = this.filteredSearchDTO.allergens.toString();

    this.router.navigate(['filtered-search'], { state: { result: this.filtersFinal, aux: this.filteredSearchDTO } });
  }

}
