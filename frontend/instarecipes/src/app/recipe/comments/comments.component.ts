import { Component, OnInit } from '@angular/core';
import { RecipesService } from 'src/app/services/recipes.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Comment } from 'src/app/Interfaces/comment.model';
import { CommentsService } from 'src/app/services/comment.service';
import { NgForm } from '@angular/forms';
import { userLikeCommentDTO } from 'src/app/Interfaces/userLikeCommentDTO.model';

export interface FinalLikes {
  id_user?: number;
  isSubcomment: boolean;
  like: boolean;
}

@Component({
  selector: 'recipe-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  comments: Comment[] = [];
  finalsComments: Comment[] = [];
  comentario: Comment;
  content: string;
  id_comment: number ;
  commentDTO;
  liked: boolean = false;
  avatar: any[] = [];
  avatarSubComments: any[] = [];
  userLikes: userLikeCommentDTO[] = [];
  finalLikes: FinalLikes[];
  finalLaik: FinalLikes;


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
  getComments() {
    this.recipesService.getComments(this.recipeService.actualRecipeID).subscribe(
      comment => {
        this.comments = comment as Comment[];
        this.comments.sort((n2, n1) => {
          if (n1.likes > n2.likes) {
            return 1;
          }
          if (n1.likes < n2.likes) {
            return -1;
          }
          return 0;
        });
        const n = this.comments.length;
        for (let i = 0; i < n; i++) {
          if (this.comments[i].isSubcomment === false) {
            this.finalsComments.push(this.comments[i]);
          }
        }
        const m = this.finalsComments.length;
        for (let j = 0; j < m; j++) {
          this.listUser(this.finalsComments[j].id);
          if (this.finalsComments[j].hasSubcomments === true) {
            this.finalsComments[j].subComments.sort((n2, n1) => {
              if (n1.likes > n2.likes) {
                return 1;
              }
              if (n1.likes < n2.likes) {
                return -1;
              }
              return 0;
            });
          }
        }
        console.log(this.finalsComments);
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
        this.getComments();
        this.content = '';
      }
    );
  }
  dislikeComment() {
    console.log('id_comment ', this.id_comment);
    this.commentService.dislikeComment(this.recipeService.actualRecipeID, this.id_comment).subscribe(
      _ => {
        this.getComments();
      }
    );
  }

  listUser(id_commeent: number) {
    console.log('mi id ' + id_commeent);
    this.recipesService.getListUserComment(id_commeent).subscribe(
      list => {
        console.log('esto es mi lista ' + JSON.stringify(list));
        list.forEach((element, index) => {
          console.log('jejeje ' + JSON.stringify(element));
          if (this.authService.user.id === element.id_user) {
            this.finalLaik.id_user = element.id_user;
            this.finalLaik.isSubcomment = element.isSubcomment;
            this.finalLaik.like = true;
            console.log('este es ahora mi finallaik ' + JSON.stringify(this.finalLaik));
            this.finalLikes.push(this.finalLaik);
          }
          if (list.length === index) {
            this.finalLaik.isSubcomment = element.isSubcomment;
            this.finalLaik.like = false;
            console.log('finallaik ' + JSON.stringify(this.finalLaik));
            this.finalLikes.push(this.finalLaik);
          }
        });
        console.log('hola caracola ' + JSON.stringify(list));
        console.log('vendo moto moto barata ' + JSON.stringify(this.finalLikes));
      }
    );
  }

  likeComment() {
    console.log(this.id_comment);
    this.commentService.likeComment(this.recipeService.actualRecipeID, this.id_comment).subscribe(
      _ => {
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
