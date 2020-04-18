import { Component, OnInit } from '@angular/core';
import { SearchService } from 'src/app/services/search.service';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipesService } from 'src/app/services/recipes.service';
import { RecipeService } from 'src/app/services/recipe.service';

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

  constructor(private searchService: SearchService,
              private domSanitizer: DomSanitizer,
              private recipesService: RecipesService,
              public recipeService: RecipeService) { }

  ngOnInit() {
    this.getRecipes();
  }

  getRecipes() {
    this.searchService.getFilteredRecipes().subscribe(
      recipe => {
        this.recipes = recipe as Recipe[];
        this.recipes.forEach(element => {
          this.recipeStepImage(element);
          this.userRecipeAvatar(element);
        });

        if (recipe.length !== 0) {
          this.recipesFounded = true;
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
      }
    );
  }

  recipeStepImage(r: Recipe) {
    console.log("sdfs ", r);
    this.recipesService.getRecipeStepImage(r.id, 1).subscribe(
      data => {
        var urlCreator = window.URL;
        this.images.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }

}
