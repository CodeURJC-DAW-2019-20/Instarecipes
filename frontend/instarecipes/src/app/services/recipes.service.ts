import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Recipe } from '../Interfaces/recipe.model';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Step } from '../Interfaces/step.model';

const BASE_URL: string = "/api/recipes/";

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  recipes: Recipe[] = [];

  constructor(private httpClient: HttpClient) { }

  refreshRecipes(page_size: number): Observable<Recipe[]> {
    return this.httpClient.get(BASE_URL + "?page=0&size=" + page_size).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Recipe[]>;
  }

  getRecipeAvatar(id_user: number): Observable<Blob> {
    let head = new HttpHeaders();
    head = head.set('Content-Type', 'image/jpeg');

    return this.httpClient.get(BASE_URL + id_user + '/avatar', {
      headers: head,
      responseType: 'blob'
    }).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Blob>;
  }

  getRecipeStepImage(id_recipe: number, n_step: number): Observable<Blob> {
    let head = new HttpHeaders();
    head = head.set('Content-Type', 'image/jpeg');
    return this.httpClient.get('/api/index/' + id_recipe + '/image/' + n_step, {
      headers: head,
      responseType: "blob"
    }).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Blob>;
  }

  getRecipeById(id_recipe: number): Observable<Recipe>{
    return this.httpClient.get(BASE_URL + id_recipe).pipe(
      catchError(error => this.handleError(error))
      ) as Observable<Recipe>;
  }
  getSteps(id_recipe: number): Observable<Step[]>{
    return this.httpClient.get(BASE_URL + id_recipe + '/steps').pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Step[]>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + '): ' + error.text());
	}
}
