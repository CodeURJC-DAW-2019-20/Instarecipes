import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Recipe } from '../Interfaces/recipe.model';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

const BASE_URL: string = "/api/recipes/?page=0&size=3";

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  recipes: Recipe[] = [];

  constructor(private httpClient: HttpClient) { }

  refreshRecipes(): Observable<Recipe[]> {
    return this.httpClient.get(BASE_URL).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Recipe[]>
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}
