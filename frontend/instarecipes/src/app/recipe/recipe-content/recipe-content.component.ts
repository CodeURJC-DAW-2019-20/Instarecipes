import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { RecipesService } from 'src/app/services/recipes.service';
import { UserService } from 'src/app/services/user.service';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { DomSanitizer } from '@angular/platform-browser';
import { User } from 'src/app/Interfaces/user.model';

@Component({
  selector: 'recipe-content',
  templateUrl: './recipe-content.component.html',
  styleUrls: ['./recipe-content.component.css']
})
export class RecipeContentComponent implements OnInit {
  recipe: Recipe;
  publications: number;
  likes: number;
  ingredients: Ingredient[];
  image: any[] = [];

  constructor(
    private recipesService: RecipesService,
    private userService: UserService,
    private domSanitizer: DomSanitizer
    ) { }

  ngOnInit() {
    this.getRecipeContent();
    this.getPublications();
    this.getAllLikes();
    this.getStepImage(9,1);
  }

  getRecipeContent() {
    this.recipesService.getRecipeById(9).subscribe(
      recipe => {
        this.recipe = recipe as Recipe;
      }
    );
  }
  getPublications() {
    this.userService.getPublications(9).subscribe(
      publications => {
        this.publications = publications as number;
      }
    );
  }
  getAllLikes() {
    this.userService.getAllLikes(9).subscribe(
      likes => {
        this.likes = likes as number;
      }
    );
  }
  getStepImage(r: number, n_step: number) {
    this.recipesService.getRecipeStepImage(r, n_step).subscribe(
      data => {
        var urlCreator = window.URL;
        this.image.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }
}
