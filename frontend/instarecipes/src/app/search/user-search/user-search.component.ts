import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/Interfaces/user.model';
import { SearchService } from 'src/app/services/search.service';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipesService } from 'src/app/services/recipes.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-search',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.css']
})
export class UserSearchComponent implements OnInit {
  users: User[] = [];
  avatar: any[] = [];
  usersFounded: boolean = false;

  constructor(
    private searchService: SearchService,
    private recipesService: RecipesService,
    private domSanitizer: DomSanitizer,
    public authService: AuthenticationService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(){
    this.searchService.getUsers().subscribe(
      users => {
        this.users = users as User[];
        if (users.length !== 0) {
          this.usersFounded = true;
        }
        this.users.forEach(element => {
          this.userAvatar(element);
        });
      }
    );
  }

  userAvatar(user: User) {
    let id_user = user.id;
    this.recipesService.getRecipeAvatar(id_user).subscribe(
      data => {
        var urlCreator = window.URL;
        this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }

  visitProfile(id: number){
    if(this.authService.user.id === id){
      this.router.navigateByUrl('/profile');
    }else{
      this.router.navigateByUrl('/users/'+id);
    }
  }
}
