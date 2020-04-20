import { Component, OnInit, Input, AfterViewInit, SimpleChanges, OnChanges, EventEmitter, Output } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { User } from 'src/app/Interfaces/user.model';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipesService } from 'src/app/services/recipes.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'publications',
  templateUrl: './publications.component.html',
  styleUrls: ['./publications.component.css']
})
export class PublicationsComponent implements OnInit, OnChanges {

  @Input()
  user_recipes: Recipe[];
  @Input()
  avatar: any;
  @Input()
  background: any;
  @Input()
  user_id: number;

  @Output()
  refresh_profile = new EventEmitter<any>();

  images: any[] = [];

  constructor(private recipesService: RecipesService, private domSanitizer: DomSanitizer,
              public authService: AuthenticationService) { }

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

  update_profile(){
    this.refresh_profile.emit(null);
  }
}
