import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

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
  handleError(error: any) {
    console.error(error);

    return Observable.throw("Server error (" + error.status + "): " + error.text())
  }
}
