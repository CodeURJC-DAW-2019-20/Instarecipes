import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

const BASE_URL: string = "/api/ranking/";

@Injectable({
  providedIn: 'root'
})
export class RankService {

  constructor(private httpClient: HttpClient) { }

  getRanking(): Observable<any[]> {
    return this.httpClient.get(BASE_URL).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<any[]>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}

}
