import { Component, OnInit } from '@angular/core';

import { User } from '../../Interfaces/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProfileService } from 'src/app/services/profile.service';
import { DomSanitizer } from '@angular/platform-browser';
import { SearchService } from 'src/app/services/search.service';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'users-list',
  templateUrl: './usersList.component.html',
  styleUrls: ['./usersList.component.css']
})
export class UsersListComponent implements OnInit {
  users: User[] = [];
  avatars: any[] = [];
  user: User;
  search: String;

  constructor(
    private router: Router,
    public authService: AuthenticationService,
    private profileService: ProfileService,
    private domSanitizer: DomSanitizer,
    public searchService: SearchService) {
    this.search = '';
  }

  ngOnInit() {
    this.getUsersList();
  }

  getUsersList() {
    this.profileService.getAllUsers().subscribe(
      user => {
        this.users = user as User[];
        this.users.forEach(element => {
          this.userAvatar(element);
        });
      }
    );
  }

  userAvatar(user: User) {
    this.profileService.getProfileAvatar(user.id).subscribe(
      avatar => {
      var urlCreator = window.URL;
      this.avatars.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(avatar)));
    }
    );
  }

  goSearch(data: NgForm) {
    console.log(this.search);
    this.searchService.search = this.search;

    const firstLetter = this.search.substring(0,1);
    if (firstLetter === "@"){
      this.router.navigate(['/users']);
    } else {
      alert("Please make the search with @");
    }
  }

}
