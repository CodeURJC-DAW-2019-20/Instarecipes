import {Component, ViewChildren, AfterViewInit, OnInit} from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { RecipesService } from 'src/app/services/recipes.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'trending-recipes',
  templateUrl: './trending.component.html',
  styleUrls: ['./trending.component.css']
})
export class TrendingComponent implements AfterViewInit, OnInit{

    trendingRecipes: Recipe[] = [];
    trendingElement: any[] = [];
    avatar: any[] = [];
    image: any[] = [];

    @ViewChildren('container') container: { toArray: () => { nativeElement: any; }[]; };

    constructor(private recipesService: RecipesService, 
      private domSanitizer: DomSanitizer){ }

    ngAfterViewInit() {
      this.trendingRecipes.forEach(element => {
        this.userRecipeAvatar(element);
        this.recipeStepImage(element, 1);
      });
      this.container.toArray().forEach((element,index) => {
        this.trendingElement.push(element.nativeElement.querySelector("div"));
        if (index > 0){
          this.trendingElement[index].classList.remove("active");
        }
      });
    }

    ngOnInit(){
      this.refreshTrending();
    }

    refreshTrending(){
      this.recipesService.getIndexTrendingRecipes().subscribe(
        recipes => {
          this.trendingRecipes = recipes as Recipe[];
        }
      );
      this.container.toArray().forEach((element,index) => {
        this.trendingElement.push(element.nativeElement.querySelector("div"));
        if (index > 0){
          this.trendingElement[index].classList.remove("active");
        }
      });
    }

    userRecipeAvatar(r: Recipe) {
      this.recipesService.getRecipeAvatar(r.username.id).subscribe(
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
        }
      );
    }

}