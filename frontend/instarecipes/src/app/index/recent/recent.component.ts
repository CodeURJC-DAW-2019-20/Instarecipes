import {Component, OnInit, ViewChild, ElementRef, OnChanges} from '@angular/core';

import { RecipesService } from '../../services/recipes.service';
import { Recipe } from '../../Interfaces/recipe.model';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipeService } from 'src/app/services/recipe.service';
import { Router } from '@angular/router';

@Component({
  selector: 'recent-recipes',
  templateUrl: './recent.component.html',
  styleUrls: ['./recent.component.css']
})

export class RecentComponent implements OnInit {
    recipes: Recipe[] = [];
    page_size: number = 2;
    recipe_counter: number = 0;
    recipe: Recipe;
    avatar: any[] = [];
    image: any[] = [];
    loadingRecents: boolean = true;

    @ViewChild('loadmore1') button1: ElementRef;
    @ViewChild('loadmore2') button2: ElementRef;

    constructor(private recipesService: RecipesService,
                private domSanitizer: DomSanitizer,
                public recipeService: RecipeService,
                private router: Router) { }

    ngOnInit() {
      this.refresh(this.page_size);
      this.recipesService.getEventSubject().subscribe((param: any) => {
        console.log('SOY LA RESPUESTA');
        if (param !== undefined) {
          this.refresh(2);
        }
      });
    }

    refresh(pS: number) {
      this.recipesService.refreshRecipes(pS).subscribe(
        recipes => {
          this.recipes = recipes as Recipe[];
          if (recipes.length <= 2) {
            this.recipeStepImage(recipes[recipes.length - 2], 1);
          }
          this.recipeStepImage(recipes[recipes.length-1], 1);
          this.button1.nativeElement.removeAttribute("hidden");
          this.button2.nativeElement.setAttribute("hidden", "");
          if (this.recipes.length > 1) { this.loadingRecents = false; }
        }
      );
    }

    loadMore(){
      this.page_size++;
      this.refresh(this.page_size);
      this.button1.nativeElement.setAttribute("hidden", "");
      this.button2.nativeElement.removeAttribute("hidden");
    }

    isOdd(i: number){
      let m = ( i % 2 === 0);
      return m;
    }

    userAvatar(r: Recipe) {
      let id_user = r.username.id;
      this.recipesService.getRecipeAvatar(id_user).subscribe(
        data => {
          var urlCreator = window.URL;
          this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
        }
      );
    }

    recipeStepImage(r: Recipe, n_step: number) {
      this.recipesService.getRecipeStepImage(r.id, n_step).subscribe(
        data => {
          var urlCreator = window.URL;
          this.image.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
          this.userAvatar(r);
        }
      );
    }

    seeRecipe(idRecipe: number) {
      this.router.navigateByUrl('/recipe/' + idRecipe);
    }
}
