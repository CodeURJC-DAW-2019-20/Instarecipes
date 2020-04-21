import { Component, OnInit, OnChanges } from '@angular/core';
import { RecipesService } from 'src/app/services/recipes.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Comment } from 'src/app/Interfaces/comment.model';
import { CommentsService } from 'src/app/services/comment.service';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'recipe-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit, OnChanges {
  comments: Comment[] = [];
  finalsComments: Comment[] = [];
  comentario: Comment;
  content: string;
  commentDTO;
  liked: boolean = false;
  avatar: any[] = [];

  constructor(
    private recipesService: RecipesService,
    private recipeService: RecipeService,
    private commentService: CommentsService,
    private domSanitizer: DomSanitizer,
    public authService: AuthenticationService

  ) { this.commentDTO = {content: '', ParentComment: 0 };
    }


  ngOnInit() {
    this.getComments();
  }

  ngOnChanges() {
    this.getComments();
  }

  getComments() {
    this.recipesService.getComments(this.recipeService.actualRecipeID).subscribe(
      comment => {
        this.comments = comment as Comment[];
        const n = this.comments.length;
        for (let i = 0; i < n; i++) {
          if (this.comments[i].isSubcomment === false) {
            this.finalsComments.push(this.comments[i]);
          }
        }
        this.finalsComments.forEach(element => {
          this.getAvatar(element);
        });
      }
    );
  }

  postComment(data: NgForm){
    console.log(this.content);
    this.commentDTO.content = this.content;

    this.commentService.postCommentToRecipe(this.recipeService.actualRecipeID, this.commentDTO).subscribe(
      _ => {
        this.commentService.triggerSomeEvent(Date());
        this.getComments();
        this.content = '';
      }
    );
  }

  dislikeComment(id_comment: number) {
    this.commentService.dislikeComment(this.recipeService.actualRecipeID, id_comment).subscribe(
      _ => {
        this.finalsComments = [];
        this.getComments();
      }

    );
  }

  likeComment(id_comment: number) {
    this.commentService.likeComment(this.recipeService.actualRecipeID, id_comment).subscribe(
      _ => {
        this.finalsComments = [];

        this.getComments();
      }
    );
  }

  getAvatar(comment: Comment) {
    this.recipesService.getRecipeAvatar(comment.userComment.id).subscribe(
      data => {
        var urlCreator = window.URL;
        this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
}


}
