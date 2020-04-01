import { Injectable } from '@angular/core';
import { User } from '../Interfaces/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {

  isLoggedUser = false;
  isAdminUser = false;
  user: User;
  auth: string;

constructor(private http: HttpClient) {
  const user = JSON.parse(localStorage.getItem('currentUser'));
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

  logout() {
    return this.http.get('/api/logout').pipe(map(response => {
      console.log("Logged out!")
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


  get isLogged(): boolean {
    return this.isLoggedUser;
  }

  set isLogged(value: boolean) {
    this.isLoggedUser;
  }

  get isLoggedAdmin(): boolean {
    return this.isLoggedAdmin;
  }

  set isLoggedAdmin(value: boolean) {
    this.isLoggedAdmin;
  }
}
