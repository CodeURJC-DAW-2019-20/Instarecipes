import { Component, OnInit } from '@angular/core';
import { SearchService } from 'src/app/services/search.service';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipesService } from 'src/app/services/recipes.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/services/profile.service';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { Category } from 'src/app/Interfaces/category.model';
import { CookingStyle } from 'src/app/Interfaces/cookingStyle.model';
import { Allergen } from 'src/app/Interfaces/allergen.model';

@Component({
  selector: 'filtered-search',
  templateUrl: './filtered-search.component.html',
  styleUrls: ['./filtered-search.component.css']
})
export class FilteredSearchComponent implements OnInit {
  recipes : Recipe[] = [];
  images: any[] = [];
  avatars: any[] = [];
  recipesFounded: boolean = false;
  filteredSearchDTO;
  filteredSearchFinal;
  subscription: Subscription;

  all_ingredients: Ingredient[] = [];
  all_categories: Category[] = [];
  all_cookingStyles: CookingStyle[] = [];
  all_allergens: Allergen[] = [];

  selected_ings: string[];
  selected_cats: string[];
  selected_cSs: string[];
  selected_alls: string[];

  loadAPI: any;

  constructor(private searchService: SearchService, private domSanitizer: DomSanitizer,
              private recipesService: RecipesService, public recipeService: RecipeService,
              private router: Router, private profileService: ProfileService) {
    this.filteredSearchFinal = this.router.getCurrentNavigation().extras.state.result;
    // if(this.router.getCurrentNavigation().extras.state.aux){
      this.filteredSearchDTO = this.router.getCurrentNavigation().extras.state.aux;
      this.checkLengths();
    // }
  }

  checkLengths(){
    if(this.filteredSearchDTO.ingredients.length > 0)
      this.selected_ings = this.filteredSearchDTO.ingredients;
    if(this.filteredSearchDTO.categories.length > 0)
      this.selected_cats = this.filteredSearchDTO.categories;
    if(this.filteredSearchDTO.cookingStyles.length > 0)
      this.selected_cSs = this.filteredSearchDTO.cookingStyles;
    if(this.filteredSearchDTO.allergens.length > 0)
      this.selected_alls = this.filteredSearchDTO.allergens;
  }

  ngOnInit() {
    this.getRecipes();
  }

  getRecipes() {
    this.searchService.getFilteredRecipes(this.filteredSearchFinal).subscribe(
      recipes => {
        this.recipes = recipes as Recipe[];
        this.recipes.forEach(element => {
          this.userRecipeAvatar(element);
        });
        if (recipes.length !== 0) {
          this.recipesFounded = true;
          this.loadAPI = new Promise(resolve => {
            console.log("resolving promise...");
            this.loadScript();
          });
          this.getIngredients();
          this.getCategories();
          this.getCookingStyles();
          this.getAllergens();
        }
      },
    );
  }

  userRecipeAvatar(r: Recipe) {
    let id_user = r.username.id;
    this.recipesService.getRecipeAvatar(id_user).subscribe(
      data => {
        var urlCreator = window.URL;
        this.avatars.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
        this.recipeStepImage(r);
      }
    );
  }

  recipeStepImage(r: Recipe) {
    this.recipesService.getRecipeStepImage(r.id, 1).subscribe(
      data => {
        var urlCreator = window.URL;
        this.images.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }

  getIngredients() {
    this.profileService.getAllIngredients().subscribe(
      ingredients => {
        this.all_ingredients = ingredients as Ingredient[];
        for (let i = 0; i < this.selected_ings.length; i++) {
          for (let j = 0; j < this.all_ingredients.length; j++) {
            if(this.selected_ings[i] == this.all_ingredients[j].ingredient){
              this.all_ingredients.splice(j, 1);
            }
          }
        }
      }
    )
  }

  getCategories() {
    this.profileService.getAllCategories().subscribe(
      categories => {
        this.all_categories = categories as Category[];
        for (let i = 0; i < this.selected_cats.length; i++) {
          for (let j = 0; j < this.all_categories.length; j++) {
            if(this.selected_cats[i] == this.all_categories[j].category){
              this.all_categories.splice(j, 1);
            }
          }
        }
      }
    )
  }

  getCookingStyles() {
    this.profileService.getAllCookingStyles().subscribe(
      cookingStyles => {
        this.all_cookingStyles = cookingStyles as CookingStyle[];
        for (let i = 0; i < this.selected_cSs.length; i++) {
          for (let j = 0; j < this.all_cookingStyles.length; j++) {
            if(this.selected_cSs[i] == this.all_cookingStyles[j].cookingStyle){
              this.all_cookingStyles.splice(j, 1);
            }
          }
        }
      }
    )
  }

  getAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => {
        this.all_allergens = allergens as Allergen[];
        for (let i = 0; i < this.selected_alls.length; i++) {
          for (let j = 0; j < this.all_allergens.length; j++) {
            if(this.selected_alls[i] == this.all_allergens[j].allergen){
              this.all_allergens.splice(j, 1);
            }
          }
        }
      }
    )
  }

  public loadScript() {
    console.log("preparing to load...");
    let node = document.createElement("script");
    node.src = 'assets/js/search.js';
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
  }
}
