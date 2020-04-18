import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { RecipesService } from 'src/app/services/recipes.service';
import { UserService } from 'src/app/services/user.service';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipeService } from 'src/app/services/recipe.service';
import { Step } from 'src/app/Interfaces/step.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/Interfaces/user.model';

@Component({
  selector: 'recipe-content',
  templateUrl: './recipe-content.component.html',
  styleUrls: ['./recipe-content.component.css']
})
export class RecipeContentComponent implements OnInit {
  recipe: Recipe;
  publications: number;
  allLikes: number;
  likes: number;
  ingredients: Ingredient[];
  image: any[] = [];
  step: Step[] = [];
  liked: boolean = false;
  likesUsers: User[] = [];
  avatar: any;

  constructor(
    private recipesService: RecipesService,
    public recipeService: RecipeService,
    private userService: UserService,
    private domSanitizer: DomSanitizer,
    public authService: AuthenticationService
    ) { }

  ngOnInit() {
    this.getRecipeContent();
    this.getStepImage(this.recipeService.actualRecipeID,1);
  }

  getRecipeContent() {
    this.recipesService.getRecipeById(this.recipeService.actualRecipeID).subscribe(
      recipe => {
        this.recipe = recipe as Recipe;
        console.log(recipe);
        this.didHeLiked();
        this.getAllLikes();
        this.getPublications();
        this.getAvatar(this.recipe.username.id);
        this.likes = recipe.likes;
      }
    );
  }

  getPublications() {
    this.userService.getPublications(this.recipe.username.id).subscribe(
      publications => {
        this.publications = publications as number;
      }
    );
  }

  getAllLikes() {
    this.userService.getAllLikes(this.recipe.username.id).subscribe(
      likes => {
        this.allLikes = likes as number;
        console.log(likes);
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
  didHeLiked() {
    this.likesUsers = this.recipe.likesUsers;
    const n = this.likesUsers.length;
    if (this.authService.isLogged) {
      for (let i = 0 ; i < n; i++) {
        if (this.authService.user.username === this.likesUsers[i].username) {
          this.liked = true;
        }
      }
    }
  }
  unlikeToRecipe() {
    this.recipesService.pressUnlikeRecipe(this.recipeService.actualRecipeID).subscribe(
      _ => {
        this.getAllLikes();
      }
    );
    console.log('disliked');
    this.liked = false;
    this.likes -= 1;
    this.allLikes -= 1;
  }
  likeToRecipe() {
    this.recipesService.pressLikeRecipe(this.recipeService.actualRecipeID).subscribe(
      _ => {
        this.getAllLikes();
      }
    );
    console.log('liked');
    this.liked = true;
    this.likes += 1;
    this.allLikes += 1;
  }
  getAvatar(id_user: number) {
      this.recipesService.getRecipeAvatar(id_user).subscribe(
        data => {
          var urlCreator = window.URL;
          this.avatar = this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data));
        }
      );
  }
}
