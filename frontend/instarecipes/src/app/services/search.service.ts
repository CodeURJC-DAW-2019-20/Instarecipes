import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Recipe } from '../Interfaces/recipe.model';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../Interfaces/user.model';
import { FilteredSearchDTO } from '../Interfaces/filteredSearchDTO.model';

const BASE_URL: string = "/api/search";

@Injectable({ providedIn: 'root' })
export class SearchService {
  search: String;
  filteredSearchDTO;

  constructor(
    private httpClient: HttpClient,

  ) {
      this.filteredSearchDTO = { ingredients: "Potatoes", categories: "", cookingStyles: "", allergens: "" }
    }


  getFilteredRecipes(): Observable<Recipe[]> {
    const body = JSON.stringify(this.filteredSearchDTO);
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
    });
    console.log(this.filteredSearchDTO);
    return this.httpClient.post(BASE_URL + "/filtered", body , { headers }).pipe(
      catchError(
        error => this.handleError(error)
      )
    ) as Observable<Recipe[]>;
  }

  getSearchRecipes() {
    console.log("im in search service ", this.search);
    return this.httpClient.get(BASE_URL + "/navbar/recipes?search=" + this.search).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Recipe[]>;
  }

  getUsers() {
    console.log("im in search service getUsers ", this.search);
    return this.httpClient.get(BASE_URL + "/navbar/users?search=" + this.search).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<User[]>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}
