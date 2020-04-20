import { AuthenticationService } from 'src/app/services/authentication.service';
import {Component, OnInit} from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { RecipesService } from 'src/app/services/recipes.service';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipeService } from 'src/app/services/recipe.service';
import { Router } from '@angular/router';

@Component({
  selector: 'trending-recipes',
  templateUrl: './trending.component.html',
  styleUrls: ['./trending.component.css']
})
export class TrendingComponent implements OnInit{

    trendingRecipes: Recipe[] = [];
    trendingElement: any[] = [];
    avatar: any[] = [];
    image: any[] = [];
    loadingTrending: boolean = true;

    constructor(private recipesService: RecipesService,
                private domSanitizer: DomSanitizer, private authService: AuthenticationService,
                public recipeService: RecipeService, private router: Router) { }

    ngOnInit(){
      console.log('actual user ', this.authService.user);
      console.log('am i logged? ', this.authService.isLogged);
      console.log('is actual user admin?', this.authService.isLoggedAdmin);
      this.refreshTrending();
    }

    refreshTrending(){
      this.recipesService.getIndexTrendingRecipes().subscribe(
        recipes => {
          this.trendingRecipes = recipes as Recipe[];
          this.loadingTrending = false;
          this.trendingRecipes.forEach(element => {
            this.userRecipeAvatar(element);
          });
        }
      );
    }

    userRecipeAvatar(r: Recipe) {
      this.recipesService.getRecipeAvatar(r.username.id).subscribe(
        data => {
          var urlCreator = window.URL;
          this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
          this.recipeStepImage(r, 1);
        }
      );
    }

    recipeStepImage(r: Recipe, n_step: number) {
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
