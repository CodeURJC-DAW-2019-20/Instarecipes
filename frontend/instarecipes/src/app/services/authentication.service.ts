/*import { Injectable } from '@angular/core';
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

  login(username: string, password: string){
    console.log(username);
    console.log(password);
    let auth = window.btoa(username + ':' + password);
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + auth,
      'X-Requested-With': 'XMLHttpRequest',
    });
    console.log(headers);
    return this.http.get<User>('/api/logInRestController', { headers }).pipe(map(user => {
      console.log(username);
      if (user) {
        this.setCurrentUser(user);
        user.authdata = auth;
        localStorage.setItem('currentUser', JSON.stringify(username));
      }
      return username;
    }));
  }

  private setCurrentUser(user: User) {
    this.isLoggedUser = true;
    this.user = user;
  }
}

*/
