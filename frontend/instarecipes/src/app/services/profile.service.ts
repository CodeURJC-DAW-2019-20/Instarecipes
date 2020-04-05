import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Allergen } from '../Interfaces/allergen.model';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

const BASE_URL: string = "/api/profile/";

@Injectable({ providedIn: 'root' })
export class ProfileService {

  constructor(private httpClient: HttpClient) { }

  getAllAllergens() {
    return this.httpClient.get(BASE_URL + "allAllergens").pipe(
      catchError(error => this.handleError(error))
    ) as Observable<Allergen[]>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}
