import {Component} from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Recipe } from '../../Interfaces/recipe.model';

//const BASE_URL: string = "http://localhost:8443/api/recipes/?page=0&size=1";

@Component({
  selector: 'recent-recipes',
  templateUrl: './recent.component.html',
  styleUrls: ['./recent.component.css']
})

export class RecentComponent{
    recipes: Recipe[] = [];
    // constructor(private httpClient: HttpClient) { 
    //   this.httpClient.get(BASE_URL).subscribe(
    //     response => this.recipes = response as any,
    //     error => console.log("Hola mundo " + error)
    //   )
    // }
    title = " Recent user's publications";
}