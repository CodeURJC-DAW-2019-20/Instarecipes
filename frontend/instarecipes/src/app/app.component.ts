import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';
import { User } from './Interfaces/user.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title : "Instarecipes";
  currentUser: User;

  constructor(private router: Router, public authService: AuthenticationService) { }

  logout() {
    this.authService.logout().subscribe(
      (response) => {
          this.router.navigate(['/index']);
      },
      (error) => console.log('Error when trying to log out: ' + error),
    );
  }
}
