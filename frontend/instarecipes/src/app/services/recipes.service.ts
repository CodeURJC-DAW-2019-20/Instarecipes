import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Recipe } from '../Interfaces/recipe.model';

const BASE_URL: string = "http://127.0.0.1:8443/api/recipes/?page=0&size=3";

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  recipes: Recipe[] = [];

  constructor(private httpClient: HttpClient) { }

  search() {
    // let url = "http://localhost:8443/api/recipes/?page=0&size=3";

    this.httpClient.get(BASE_URL).subscribe(
      response => {
        let data: any = response;
        console.log("HOla: " + data);
        for (var i = 0; i < data.items.length; i++) {
          let recipe = data.items[i];
          this.recipes.push(recipe);
        }
      },
      error => console.error(error)
    );
  }

}
