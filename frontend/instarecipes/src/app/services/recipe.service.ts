import { Injectable } from '@angular/core';
import { Recipe } from '../Interfaces/recipe.model';
import { RecipesService } from './recipes.service';
import { User } from '../Interfaces/user.model';
import { HttpClient } from '@angular/common/http';

const BASE_URL: string = "/api/recipes/";

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  actualRecipeID : number;
  recipe: Recipe;
  nSteps: number;

  constructor(
    private httpClient: HttpClient
  ) { }

  setRecipeID(id: number){
    this.actualRecipeID = id;
    console.log("recipe id ", this.actualRecipeID);
  }
  setNsteps(n: number){
    this.nSteps = n;
  }

  getNsteps(){
    return this.nSteps;
  }
}
