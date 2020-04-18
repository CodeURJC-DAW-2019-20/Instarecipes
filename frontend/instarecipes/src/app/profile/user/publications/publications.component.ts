import { Component, OnInit, Input, AfterViewInit, SimpleChanges, OnChanges } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { User } from 'src/app/Interfaces/user.model';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipesService } from 'src/app/services/recipes.service';

@Component({
  selector: 'publications',
  templateUrl: './publications.component.html',
  styleUrls: ['./publications.component.css']
})
export class PublicationsComponent implements OnInit, OnChanges {

  @Input()
  user_recipes: Recipe[];

  images: any[] = [];

  constructor(private recipesService: RecipesService, private domSanitizer: DomSanitizer) { }

  ngOnInit() {
  }

  ngOnChanges(){
    if(this.user_recipes.length > 0){
      this.loadImages();
    }
  }

  loadImages(){
    this.user_recipes.forEach(recipe => {
      this.recipesService.getRecipeStepImage(recipe.id, 1).subscribe(
        data => {
          var urlCreator = window.URL;
          this.images.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
        }
      );
    });
  }
}
