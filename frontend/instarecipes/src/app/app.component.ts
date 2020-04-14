import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';
import { User } from './Interfaces/user.model';
import { NgForm } from '@angular/forms';
import { stringify } from 'querystring';
import { SearchService } from './services/search.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title : "Instarecipes";
  currentUser: User;
  search: String;

  constructor(
    private router: Router,
    public authService: AuthenticationService,
    public searchService: SearchService) {
      this.search = '';
    }

  logout() {
    this.authService.logout().subscribe(
      (response) => {
          this.router.navigate(['/index']);
      },
      (error) => console.log('Error when trying to log out: ' + error),
    );
  }

  goSearch(data: NgForm) {
    console.log(this.search);
    // this.searchService.search = this.search;

    const firstLetter = this.search.substring(0,1);
    if (firstLetter === "@"){
      this.router.navigate(['/users']);
    } else {
      this.router.navigate(['/recipe-search']);
    }
  }
}
