import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { SearchService } from 'src/app/services/search.service';
import { RecipesService } from 'src/app/services/recipes.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recipe-search',
  templateUrl: './recipe-search.component.html',
  styleUrls: ['./recipe-search.component.css']
})
export class RecipeSearchComponent implements OnInit {
  recipes: Recipe[] = [];
  recipesFounded: boolean = false;
  avatar: any[] = [];
  image: any[] = [];

  constructor (
    private searchService: SearchService,
    private recipesService: RecipesService,
    private domSanitizer: DomSanitizer,
    public authService: AuthenticationService,
    private router: Router,
    ) {
      }

  ngOnInit(): void {
    this.getSearch();
  }

  getSearch() {
    this.searchService.getSearchRecipes().subscribe(
      recipes => {
        this.recipes = recipes as Recipe[];
        if (recipes.length !== 0) {
          this.recipesFounded = true;
        }
        this.recipes.forEach(element => {
          this.userRecipeAvatar(element);
          this.recipeStepImage(element, 1);
        });
      }
    );
  }

  userRecipeAvatar(r: Recipe) {
    let id_user = r.username.id;
    this.recipesService.getRecipeAvatar(id_user).subscribe(
      data => {
        var urlCreator = window.URL;
        this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }

  recipeStepImage(r: Recipe, n_step: number) {
    console.log("sdfs ", r);
    this.recipesService.getRecipeStepImage(r.id, n_step).subscribe(
      data => {
        var urlCreator = window.URL;
        this.image.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }

  seeRecipe(idRecipe: number) {
    this.router.navigateByUrl('/recipe/' + idRecipe);
  }
}
