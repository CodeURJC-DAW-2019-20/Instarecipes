import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Recipe } from './Interfaces/recipe.model';
import { RecipesService } from './services/recipes.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = "Instarecipes";
  recipes: Recipe[] = [];
  constructor (private recipesService: RecipesService){ }

  search(){
    this.recipesService.search();
  }
}
