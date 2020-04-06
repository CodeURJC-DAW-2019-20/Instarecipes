import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Recipe } from '../Interfaces/recipe.model';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { CookingStyle } from '../Interfaces/cookingStyle.model';

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

    return this.httpClient.get(BASE_URL + id_user + "/avatar", {
      headers: head,
      responseType: "blob"
    }).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Blob>;
  }

  getRecipeStepImage(id_recipe: number, n_step: number): Observable<Blob> {
    let head = new HttpHeaders();
    head = head.set('Content-Type', 'image/jpeg');
    return this.httpClient.get("/api/index/" + id_recipe + "/image/" + n_step, {
      headers: head,
      responseType: "blob"
    }).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Blob>;
  }

  getIndexTrendingRecipes(): Observable<Recipe[]>{
    return this.httpClient.get("/api/trending").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Recipe[]>;
  }

  postRecipe(recipe: Recipe) {
    const body = JSON.stringify(recipe);
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
    });
    this.httpClient
        .post<Recipe>("/api/index", body, { headers })
        .pipe(catchError((error) => this.handleError(error)));
}

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}
