import {Component, OnInit} from '@angular/core';

import { RecipesService } from '../../services/recipes.service';
import { Recipe } from '../../Interfaces/recipe.model';

@Component({
  selector: 'recent-recipes',
  templateUrl: './recent.component.html',
  styleUrls: ['./recent.component.css']
})

export class RecentComponent implements OnInit{
    title = " Recent user's publications";
    recipes: Recipe[] = [];
    constructor (private recipesService: RecipesService){ }
  
    ngOnInit(){
      this.refresh();
    }

    refresh() {
      this.recipesService.refreshRecipes().subscribe(
        recipes => this.recipes = recipes as Recipe[]
      );
    }

}