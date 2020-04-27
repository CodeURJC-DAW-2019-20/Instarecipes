import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { RecipeService } from 'src/app/services/recipe.service';
import { Router } from '@angular/router';

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

  constructor( public recipeService: RecipeService, private router: Router){ }

  ngOnInit(){ }

  seeRecipe(idRecipe: number) {
    this.closebutton.nativeElement.click();
    this.router.navigateByUrl('/recipe/' + idRecipe);
  }
}
