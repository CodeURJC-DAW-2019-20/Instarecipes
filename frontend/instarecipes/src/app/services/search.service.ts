import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Recipe } from '../Interfaces/recipe.model';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

const BASE_URL: string = "/api/search";

@Injectable({ providedIn: 'root' })
export class SearchService {
  search: String;

  constructor(private httpClient: HttpClient) { }

  getSearchRecipes() {
    console.log("im in search service ", this?.search);
    return this.httpClient.get(BASE_URL + "/navbar/recipes?search=" + this.search).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Recipe[]>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}
