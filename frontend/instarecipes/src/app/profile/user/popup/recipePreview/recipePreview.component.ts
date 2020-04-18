import { Component, OnInit, Input } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';

@Component({
  selector: 'popup-recipe-preview',
  templateUrl: './recipePreview.component.html',
  styleUrls: ['./recipePreview.component.css']
})
export class RecipePreviewComponent implements OnInit {

  @Input()
  recipe: Recipe;
  @Input()
  i: number;
  @Input()
  image: any;

  constructor(){ }

  ngOnInit(){ }

}
