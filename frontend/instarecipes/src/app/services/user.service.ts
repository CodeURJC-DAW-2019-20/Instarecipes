import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../Interfaces/user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  jsonData;
  finalData;

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

  setFinalData(val: object) {
    this.finalData = Object.assign(this.getJSONData(), val);

  }

  getFinalData() {
    return this.finalData;
  }

}
