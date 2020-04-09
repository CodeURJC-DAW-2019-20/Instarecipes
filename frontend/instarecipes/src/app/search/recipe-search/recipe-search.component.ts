import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-recipe-search',
  templateUrl: './recipe-search.component.html',
  styleUrls: ['./recipe-search.component.css']
})
export class RecipeSearchComponent implements OnInit {
  recipes: Recipe[] = [];
  recipesFounded: boolean = false;

  constructor (private searchService: SearchService){ }


  ngOnInit(): void {
    this.getSearch();
  }

  getSearch() {
    this.searchService.getSearchRecipes().subscribe(
      recipes => {
        this.recipes = recipes as Recipe[];
      }
    );
    console.log(this.recipes.length);

    if (this.recipes.length != 0) {
      this.recipesFounded = true;
    }
  }
}
