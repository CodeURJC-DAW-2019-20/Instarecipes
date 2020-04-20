import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/Interfaces/comment.model';

const BASE_URL: string = "/api/recipes/";

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  comments: Comment[] = [];
  constructor(private httpClient: HttpClient) { }

  getCommentsFromRecipe(id_recipe: number) {
    return this.httpClient.get(BASE_URL + id_recipe + "/comments/").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Comment[]>;
  }

  postCommentToRecipe(id_recipe: number, commentDTO: any) {
    const body = JSON.stringify(commentDTO);
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
    });
    return this.httpClient.post(BASE_URL + id_recipe + '/comments/', body, {headers}).pipe(
      catchError(error => this.handleError(error))
    );
  }

  dislikeComment(id_recipe: number, id_comment: number): Observable<Comment> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.httpClient.post(BASE_URL + id_recipe + '/comments/' + id_comment + '/commentUnpressLike', {headers}).pipe(
      catchError(error => this.handleError(error))
    )as Observable<Comment>;
  }

  likeComment(id_recipe: number, id_comment: number): Observable<Comment> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.httpClient.post(BASE_URL + id_recipe + '/comments/' + id_comment + '/commentPressLike', {headers}).pipe(
      catchError(error => this.handleError(error))
    )as Observable<Comment>;
  }

  handleError(error: any) {
    console.error(error);

    return Observable.throw('Server error (' + error.status + '):' + error.text())
  }
}
