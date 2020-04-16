import { Injectable } from '@angular/core';
import { Recipe } from '../Interfaces/recipe.model';
import { RecipesService } from './recipes.service';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  actualRecipeID : number;
  recipe: Recipe;

  constructor(
    private recipesService: RecipesService,
  ) { }

  setRecipeID(id : number){
    this.actualRecipeID = id;
    console.log("recipe id ", this.actualRecipeID);
    this.getRecipeContent();
  }

  getRecipeContent() {
    this.recipesService.getRecipeById(this.actualRecipeID).subscribe(
      recipe => {
        this.recipe = recipe as Recipe;
      }
    );
    console.log(this.recipe);
  }

}
