import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../Interfaces/user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  jsonData;
  constructor(
    private http: HttpClient,
    ) {
      this.jsonData = {}
    }

  getAll() {
    return this.http.get<User[]>(`${config.apiUrl}/users`);
  }

  setJSONData(val: object) {
    this.jsonData = val;
  }
  getJSONData() {
    return this.jsonData;
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
