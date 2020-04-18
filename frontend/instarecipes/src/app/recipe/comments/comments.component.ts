import { Component, OnInit } from '@angular/core';
import { RecipesService } from 'src/app/services/recipes.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'recipe-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  comments: Comment[] = [];
  avatar: any[] = [];
  constructor(
    private recipesService: RecipesService,
    private recipeService: RecipeService,
    private domSanitizer: DomSanitizer

  ) {}

  ngOnInit() {
  }
  getComments() {
    this.recipesService.getComments(this.recipeService.actualRecipeID).subscribe(
      comment => {
        this.comments = comment as Comment[];

        for (let i = 0; i < this.comments.length; i++){
          this.getAvatar(comment[i]);
        }
      }
    );
  }
  getAvatar(id_user: number) {
    this.recipesService.getRecipeAvatar(id_user).subscribe(
      data => {
        var urlCreator = window.URL;
        this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
}

}
