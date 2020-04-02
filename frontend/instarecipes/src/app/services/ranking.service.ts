import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { RankingRecipe } from '../Interfaces/rankRecipe.model';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

const BASE_URL: string = "/api/ranking/";

@Injectable({
  providedIn: 'root'
})
export class RankService {
    [x: string]: any;

  rankrecipes: RankingRecipe[] = [];

  constructor(private httpClient: HttpClient) { }
  
  
  getRanking(): Observable<RankingRecipe[]> {
    return this.httpClient.get(BASE_URL).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<RankingRecipe[]>;
  }


}