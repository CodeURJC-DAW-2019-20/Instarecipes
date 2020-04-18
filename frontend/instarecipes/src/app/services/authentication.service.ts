import { Injectable } from '@angular/core';
import { User } from '../Interfaces/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  isLoggedUser = false;
  isLoggedAdmin = false;
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
    return this.http.get<User>('/api/login', { headers }).pipe(
      map(user => {
        console.log(user);
        if (user) {
          this.setCurrentUser(user);
          user.authdata = auth;
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
        return user;
      }),
    );
  }

  logout() {
    return this.http.get('/api/logout').pipe(
      map(response => {
        console.log("Logged out!")
        this.removeCurrentUser();
        return response;
      }),
    );
  }

  private setCurrentUser(user: User) {
    this.isLoggedUser = true;
    this.user = user;
    this.isLoggedAdmin = this.user.roles.indexOf('ROLE_ADMIN') !== -1;
  }

  removeCurrentUser() {
    localStorage.removeItem('currentUser');
    this.isLoggedUser = false;
    this.isLoggedAdmin = false;
  }


  get isLogged(): boolean {
    return this.isLoggedUser;
  }

  get isAdmin(): boolean {
    return this.isLoggedAdmin;
  }

  register(user: User ): Observable<User> {
    console.log('im in authentication.service register ');
    console.log(user);
    return this.http.post('/api/signup', user).pipe(
      catchError(
        error => this.handleError(error)
      )
    ) as Observable<User>
  }

  private handleError(error: any) {
		console.error(error);
		return Observable.throw('Server error (' + error.status + '): ' + error.text());
	}


}
