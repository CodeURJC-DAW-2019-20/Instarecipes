import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { NgForm } from '@angular/forms';
import { CookingStyle } from 'src/app/Interfaces/cookingStyle.model';
import { ProfileService } from 'src/app/services/profile.service';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { Category } from 'src/app/Interfaces/category.model';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RecipeDTO } from 'src/app/Interfaces/recipeDTO.model.js';
import { RecipesService } from 'src/app/services/recipes.service.js';
import { Router } from '@angular/router';
import { Step } from 'src/app/Interfaces/step.model.js';

@Component({
  selector: 'popup-add',
  templateUrl: './addRecipe.component.html',
  styleUrls: ['./addRecipe.component.css']
})
export class AddRecipeComponent implements OnInit{

  stepsList: Step[] = [];

  recipe: RecipeDTO;
  allCookingStyles: CookingStyle[] = [];
  allIngredients: Ingredient[] = [];
  allCategories: Category[] = [];
  allAllergens: Allergen[] = [];

  catString: string = "";
  ingString: string = "";
  
  @ViewChild('ingredientsString') ingredientsString: ElementRef;
  @ViewChild('categoriesString') categoriesString: ElementRef;
  @ViewChild('ingredientsList') ingList: ElementRef;
  @ViewChild('categoriesList') catList: ElementRef;
  @ViewChild('otherSteps') otherSteps: ElementRef;
  @ViewChild('otherImages') otherImages: ElementRef;
  @ViewChild('mainImage') mainImage: ElementRef;

  firstStepAux: string = '';
  allergenAux: string = '';
  withImageAux: string[] = [];
  stepsAux: string[] = [];
  ingredientsAux: string[] = [];
  categoriesAux: string[] = [];
  cookingStylesAux: string = '';
  fileToUpload: File = null;
  stepsFiles: Map<number,File> = new Map();

  constructor (private profileService: ProfileService, private recipeService: RecipesService,
    public authService: AuthenticationService) {
      this.initConstructor();
  }

  initConstructor(){
    this.recipe = { user: null, title: '', description: '', duration: '', difficulty: '', firstStep: '', 
    allergen:'', withImage: [], steps: [], ingredients: [], categories: [], cookingStyles: [] };
    this.fileToUpload = null;
    this.stepsFiles = new Map();
    this.stepsList = [];
  }

  ngOnInit(){
    this.loadStuff();
  }

  receiveMap($event) {
    $event.forEach((value, key) => {
      console.log("Value: " + value + " Key: " + key);
      this.stepsFiles.set(key, value);
    });
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

  addStep(description: string) {
    let s = {content: description, number: this.stepsList.length+1};
    this.stepsList.push(s);
    console.log("Added new step");
	}

	removeStep() {
    this.stepsList.pop();
    console.log("Removed last step");
	}

  handleFileInput(files: FileList) {
    this.fileToUpload = files[0];
  }

  postRecipe(){
    this.recipe.user = this.authService.user;
    this.recipe.ingredients.push(this.ingredientsString.nativeElement.value);
    this.recipe.categories.push(this.categoriesString.nativeElement.value);
    this.recipe.cookingStyles.push(this.cookingStylesAux);
    this.recipe.steps.push(this.otherSteps.nativeElement.value);
    this.recipe.withImage.push(this.otherImages.nativeElement.value);
    console.log(this.recipe);
    console.log(this.fileToUpload);
    let totalRecipes = [];
    this.recipeService.postRecipe(this.recipe).subscribe(
      _ => {
        this.recipeService.refreshRecipes(100).subscribe(
          recipes => {
            totalRecipes = recipes;
            console.log("Tamanio: " + totalRecipes.length);
            this.recipeService.postImageStep(this.fileToUpload, totalRecipes.length+2, 1).subscribe(
              imagen => {
                console.log("Imagen subida" + imagen);
                this.stepsFiles.forEach((value, key) => {
                  this.recipeService.postImageStep(value, totalRecipes.length+2, key).subscribe(
                    file => {
                      console.log("Imagen del paso " + key + " subida exitosamente: " + file);
                    })
                });
                this.initConstructor();
              },
              (error: Error) => console.error('Error creating recipe step image: ' + error),
            );
          }
        );
      },
      (error: Error) => console.error('Error creating new recipe: ' + error),
    );
  }
}