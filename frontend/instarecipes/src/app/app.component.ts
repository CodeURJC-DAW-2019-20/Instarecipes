import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';
import { User } from './Interfaces/user.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title : "Instarecipes";
  currentUser: User;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    //this.authenticationService.user?.subscribe(x => this.currentUser = x);
  }

  ngOnInit(): void {

  }

  logout() {
  this.authenticationService.logout();
  this.router.navigate(['/login']);
  }
}
