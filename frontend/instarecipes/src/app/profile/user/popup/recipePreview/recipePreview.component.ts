import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { RecipeService } from 'src/app/services/recipe.service';

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

  @ViewChild('closebutton') closebutton: ElementRef;

  constructor( public recipeService: RecipeService){ }

  ngOnInit(){ }

  seeRecipe() {    
    this.closebutton.nativeElement.click();
    this.recipeService.setRecipeID(this.recipe.id);
  }
}
