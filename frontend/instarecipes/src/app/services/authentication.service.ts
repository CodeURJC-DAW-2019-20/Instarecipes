import { Injectable } from '@angular/core';
import { User } from '../Interfaces/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class AuthenticationService {

  isLoggedUser = false;
  isAdminUser = false;
  user: User;
  auth: string;

constructor(private http: HttpClient) {
  let user = JSON.parse(localStorage.getItem('currentUser'));
  if (user) {
    this.setCurrentUser(user);
  }
}

  login(user: string, password: string){
    console.log("im in authentication.service login ")
    console.log(user);
    console.log(password);
    let auth = window.btoa(user + ':' + password);
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + auth,
      'X-Requested-With': 'XMLHttpRequest',
    });
    console.log(headers);
    return this.http.get<User>('/api/login', { headers }).pipe(map(user => {
      console.log(user);
      if (user) {
        this.setCurrentUser(user);
        user.authdata = auth;
        localStorage.setItem('currentUser', JSON.stringify(user));
      }
      return user;
    }));
  }

  logOut() {
    return this.http.get('/api/logout').pipe(map(response => {
      this.removeCurrentUser();
      return response;
    }));
  }

  private setCurrentUser(user: User) {
    this.isLoggedUser = true;
    this.user = user;
  }

  removeCurrentUser() {
    localStorage.removeItem('currentUser');
    this.isLoggedUser = false;
    this.isAdminUser = false;
  }
}
