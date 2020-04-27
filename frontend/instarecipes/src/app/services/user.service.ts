import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../Interfaces/user.model';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

const BASE_URL: string = "/api/users/"
@Injectable({ providedIn: 'root' })
export class UserService {
  jsonData;
  finalData;

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

  getAvatarProfile(id: number): Observable<Blob> {
    return this.http.get(BASE_URL + id + '/image').pipe(
      catchError(error => this.handleError(error))
     ) as Observable<Blob>;
  }

  followUser(id: number): Observable<User[]>{
    return this.http.put(BASE_URL + id + '/followAction', {}).pipe(
      catchError(error => this.handleError(error))
     ) as Observable<User[]>;
  }

  unfollowUser(id: number): Observable<User[]>{
    return this.http.put(BASE_URL + id + '/unfollowAction', {}).pipe(
      catchError(error => this.handleError(error))
     ) as Observable<User[]>;
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
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
