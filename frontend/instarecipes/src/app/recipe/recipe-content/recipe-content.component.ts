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
import { Router, ActivatedRoute } from '@angular/router';
import { CommentsService } from 'src/app/services/comment.service';
import { Comment } from 'src/app/Interfaces/comment.model';
import { toInteger } from '@ng-bootstrap/ng-bootstrap/util/util';

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
  commentsArr: Comment[] = [];
  comments: number;
  avatar: any;

  constructor(
    private recipesService: RecipesService,
    public recipeService: RecipeService,
    private userService: UserService,
    private domSanitizer: DomSanitizer,
    public authService: AuthenticationService,
    public router: Router,
    private route: ActivatedRoute,
    private commentService: CommentsService
    ) { }

  ngOnInit() {
    console.log('ID RECIPE: ' + this.route.snapshot.paramMap.get('id'));
    if (!this.route.snapshot.paramMap.get('id')) {
      this.recipeService.actualRecipeID = this.recipeService.actualRecipeID;
    } else {
      this.recipeService.actualRecipeID = +(this.route.snapshot.paramMap.get('id'));
    }
    this.refresh();
  }

  refresh() {
    this.getRecipeContent();
    this.getStepImage(this.recipeService.actualRecipeID, 1);
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
        this.commentService.getCommentsFromRecipe(this.recipeService.actualRecipeID).subscribe(
          comment => {
            this.commentsArr = comment as Comment[];
            this.comments = comment.length;
          }
        );
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

  visitProfile(){
    if(this.authService.user.id === this.recipe.username.id){
      this.router.navigateByUrl('/profile');
    }else{
      this.router.navigateByUrl('/users/'+this.recipe.username.id);
    }
  }

}
