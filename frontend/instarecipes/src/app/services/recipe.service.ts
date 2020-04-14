import { Injectable } from '@angular/core';
import { Recipe } from '../Interfaces/recipe.model';
import { RecipesService } from './recipes.service';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  actualRecipeID : number;
  recipe: Recipe;

  constructor() { }

  setRecipeID(id : number){
    this.actualRecipeID = id;
    console.log("recipe id ", this.actualRecipeID);
  }

}
