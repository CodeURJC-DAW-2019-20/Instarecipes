import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../Interfaces/user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  jsonData;

  formContent: any;
  formData: any;

  constructor(
    private http: HttpClient,
    ) { }

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
