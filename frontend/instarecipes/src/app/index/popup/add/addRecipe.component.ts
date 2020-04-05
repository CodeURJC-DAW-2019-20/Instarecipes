import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { NgForm } from '@angular/forms';
import { CookingStyle } from 'src/app/Interfaces/cookingStyle.model';
import { ProfileService } from 'src/app/services/profile.service';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { Category } from 'src/app/Interfaces/category.model';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'popup-add',
  templateUrl: './addRecipe.component.html',
  styleUrls: ['./addRecipe.component.css']
})
export class AddRecipeComponent implements OnInit{

  recipe: Recipe;
  allCookingStyles: CookingStyle[] = [];
  allIngredients: Ingredient[] = [];
  allCategories: Category[] = [];
  allAllergens: Allergen[] = [];

  catString: string = "";
  ingString: string = "";

  @ViewChild('ingredientsList') ingList: ElementRef;
  @ViewChild('categoriesList') catList: ElementRef;

  constructor (private profileService: ProfileService, public authService: AuthenticationService) {
    this.recipe = {username: null, title: '', description: '', duration: '', difficulty: '', ingredients: null, categories: null};
  }

  ngOnInit(){
    this.loadStuff();
  }

  loadStuff(){
    this.profileService.getAllCookingStyles().subscribe(
      cookingStyles => this.allCookingStyles = cookingStyles
    );
    this.profileService.getAllIngredients().subscribe(
      ingredients => {
        this.allIngredients = ingredients;
        this.allIngredients.forEach(element => this.ingString = this.ingString + element.ingredient + ",");
        this.ingList.nativeElement.setAttribute("value",this.ingString);
        this.profileService.getAllCategories().subscribe(
          categories => {
            this.allCategories = categories;
            this.allCategories.forEach(element => this.catString = this.catString + element.category + ",");
            this.catList.nativeElement.setAttribute("value",this.catString);
            import('../../../../assets/js/add_recipe.js');
          }
        );
      }
    );
    this.profileService.getAllAllergens().subscribe(
      allergens => this.allAllergens = allergens
    );
  }

  starClick(i: number){
    switch (i) {
      case 1:
        this.recipe.difficulty = "Easy";
        break;
      case 2:
        this.recipe.difficulty = "Medium";
        break;
      case 3:
        this.recipe.difficulty = "Hard";
        break;
      case 4:
        this.recipe.difficulty = "Extreme";
        break;
      default:
        this.recipe.difficulty = "";
        break;
    }
  }

  postRecipe(data: NgForm){
    this.recipe.username = this.authService.user;
    
  }
}