import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Recipe } from '../Interfaces/recipe.model';
import { catchError } from 'rxjs/operators';
import { Observable, BehaviorSubject } from 'rxjs';
import { Step } from '../Interfaces/step.model';
import { RecipeDTO } from '../Interfaces/recipeDTO.model';

const BASE_URL: string = '/api/recipes/';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {
  recipes: Recipe[] = [];

  constructor(private httpClient: HttpClient) { }

  private eventSubject = new BehaviorSubject<any>(undefined);

  triggerSomeEvent(param: any) {
    console.log('me ejecuto despues del post add recipe', param);
    this.eventSubject.next(param);
  }

  getEventSubject(): BehaviorSubject<any> {
      return this.eventSubject;
  }

  refreshRecipes(page_size: number): Observable<Recipe[]> {
    return this.httpClient.get(BASE_URL + '?page=0&size=' + page_size).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Recipe[]>;
  }

  getLastRecipeId(): Observable<number>{
    return this.httpClient.get(BASE_URL + "last").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<number>;
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

  getIndexTrendingRecipes(): Observable<Recipe[]>{
    return this.httpClient.get("/api/trending").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Recipe[]>;
  }

  postRecipe(recipe: RecipeDTO) {
    const body = JSON.stringify(recipe);
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
    });
    return this.httpClient.post<RecipeDTO>("/api/index", body, { headers }).pipe(
      catchError(error => this.handleError(error))
    );
  }

  postImageStep(imageFile: File, recipe_id: number, step: number): Observable<any[]> {
    console.log('recipe service mi file', imageFile);
    let data: FormData = new FormData();
    data.append('imageFile', imageFile);
    return this.httpClient.post<any>("/api/index/"+recipe_id+"/image/"+step, data).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<any[]>;
  }

  pressUnlikeRecipe(id_recipe: number) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.httpClient.post(BASE_URL + id_recipe + '/recipeUnpressLike', {headers}).pipe(
      catchError(
        error => this.handleError(error)
      )
    );
  }
  pressLikeRecipe(id_recipe: number) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.httpClient.post(BASE_URL + id_recipe + '/recipePressLike', {headers}).pipe(
      catchError(
        error => this.handleError(error)
      )
    );
  }
  getComments(id_recipe: number): Observable<Comment[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
  });
    return this.httpClient.get(BASE_URL + id_recipe + '/comments/').pipe(
      catchError(
        error => this.handleError(error)
      )
    ) as Observable<Comment[]>;
  }
  private handleError(error: any) {
		console.error(error);
		return Observable.throw('Server error (' + error.status + '): ' + error.text())
	}
}
