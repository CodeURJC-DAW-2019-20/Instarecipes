import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Allergen } from '../Interfaces/allergen.model';
import { Category } from '../Interfaces/category.model';
import { Ingredient } from '../Interfaces/ingredient.model';
import { CookingStyle } from '../Interfaces/cookingStyle.model';
import { User } from '../Interfaces/user.model';
import { Recipe } from '../Interfaces/recipe.model';

const BASE_URL: string = "/api/profile";

@Injectable({ providedIn: 'root' })
export class ProfileService {

  constructor(private httpClient: HttpClient) { }
  getActualUser() : Observable<any>{
    return this.httpClient.get(BASE_URL).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<any>;
  
  }
  getAllAllergens() : Observable<Allergen[]> {
    return this.httpClient.get(BASE_URL + "/allAllergens").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Allergen[]>;
  }

  getAllIngredients(): Observable<Ingredient[]>{
    return this.httpClient.get(BASE_URL + "/allIngredients").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Ingredient[]>;
  }

  getAllCategories(): Observable<Category[]>{
    return this.httpClient.get(BASE_URL + "/allCategories").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Category[]>;
  }

  getAllCookingStyles(): Observable<CookingStyle[]>{
    return this.httpClient.get(BASE_URL + "/allCookingStyles").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<CookingStyle[]>;
  }

  getUser(id_user: number): Observable<User> {
    return this.httpClient.get("/api/users/" + id_user).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<User>;
  }

  getUserRecipes(id_user:number): Observable<Recipe[]> {
    return this.httpClient.get(BASE_URL + "/" + id_user + "/recipes" ).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Recipe[]>;
  }

  getProfileAvatar(id_user: number): Observable<Blob> {
    let head = new HttpHeaders();
    head = head.set('Content-Type', 'image/jpeg');

    return this.httpClient.get(BASE_URL + "/" + id_user + "/image", {
      headers: head, responseType: "blob"}).pipe(
        catchError(error => this.handleError(error))
    ) as Observable<Blob>;
  }

  updateProfileAvatar(selectedFile: File): Observable<boolean> {
    const data: FormData = new FormData();
    data.append('avatar', selectedFile);
    return this.httpClient.post(BASE_URL +  "/update/avatar", data).pipe(
      catchError(
        error => this.handleError(error)
      )
    ) as Observable<boolean>;
  }

  getUserFollowing(id_user: number): Observable<User[]> {
    return this.httpClient.get("/api/users/" + id_user + "/following").pipe(
        catchError(error => this.handleError(error))
    ) as Observable<User[]>;
  }

  getUserFollowers(id_user: number): Observable<User[]> {
    return this.httpClient.get("/api/users/" + id_user + "/followers").pipe(
        catchError(error => this.handleError(error))
    ) as Observable<User[]>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}
