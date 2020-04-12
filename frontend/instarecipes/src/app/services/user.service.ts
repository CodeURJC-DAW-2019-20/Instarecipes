import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../Interfaces/user.model';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

const BASE_URL: string = "api/users/"
@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<User[]>(`${config.apiUrl}/users`);
  }

  getPublications(id: number): Observable<number> {
    return this.http.get(BASE_URL + 'publications/' + id).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<number>;
  }

  getAllLikes(id: number): Observable<number>{
    return this.http.get(BASE_URL + 'likes/' + id).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<number>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}

}
/*import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

const BASE_URL = '/api/user/';

@Injectable({ providedIn: 'root' })
export class UserService {

	constructor(private httpClient: HttpClient) { }

  login(username:string, password:string) {
    return this.httpClient.post('/api/loginRestController', {
      email: username,
      password: password,
    });
  }

	private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}*/
