import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Allergen } from '../Interfaces/allergen.model';
import { Category } from '../Interfaces/category.model';
import { Ingredient } from '../Interfaces/ingredient.model';
import { CookingStyle } from '../Interfaces/cookingStyle.model';

const BASE_URL: string = "/api/profile/";

@Injectable({ providedIn: 'root' })
export class ProfileService {

  constructor(private httpClient: HttpClient) { }

  getAllAllergens() : Observable<Allergen[]> {
    return this.httpClient.get(BASE_URL + "allAllergens").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Allergen[]>;
  }

  getAllIngredients(): Observable<Ingredient[]>{
    return this.httpClient.get(BASE_URL + "allIngredients").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Ingredient[]>;
  }

  getAllCategories(): Observable<Category[]>{
    return this.httpClient.get(BASE_URL + "allCategories").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Category[]>;
  }

  getAllCookingStyles(): Observable<CookingStyle[]>{
    return this.httpClient.get(BASE_URL + "allCookingStyles").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<CookingStyle[]>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}
