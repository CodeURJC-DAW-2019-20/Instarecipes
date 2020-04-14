import { Component, OnInit } from '@angular/core';
import { RecipesService } from 'src/app/services/recipes.service';
import { Step } from 'src/app/Interfaces/step.model';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'recipe-steps',
  templateUrl: './recipe-steps.component.html',
  styleUrls: ['./recipe-steps.component.css']
})
export class RecipeStepsComponent implements OnInit {
  step: Step[] = [];
  recipe: Recipe;
  image: any[] = [];

  constructor(
    private recipesService: RecipesService,
    private domSanitizer: DomSanitizer,
    ) { }

  ngOnInit() {
    this.getAllSteps(9);
    this.getRecipeContent();
  }
  getRecipeContent() {
    this.recipesService.getRecipeById(9).subscribe(
      recipe => {
        this.recipe = recipe as Recipe;
      }
    );
  }

  getAllSteps(id: number) {
    this.recipesService.getSteps(id).subscribe(
    step => {
      this.step = step as Step[];
      this.getAllimage(id, this.step);
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

  getAllimage(id_recipe: number, step: Step[]) {
    const n = step.length;
    console.log(n);
    for (let i = 0; i <= n; i++) {
      this.getStepImage(id_recipe, i);
    }
    console.log(this.image);
  }
}
