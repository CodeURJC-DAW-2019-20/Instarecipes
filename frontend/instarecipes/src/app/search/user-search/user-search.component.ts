import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/Interfaces/user.model';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-user-search',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.css']
})
export class UserSearchComponent implements OnInit {
  users: User[];

  constructor(
    private searchService: SearchService,
  ) { }

  ngOnInit() {

  }

/*  getUsers(){
    this.searchService.getUsers().subscribe(
      users => {
        this.searchService = users as User[];
        this.searchService.forEach(element => {
          this.userRecipeAvatar(element);
          this.recipeStepImage(element, 1);
        });
      }
    );
  }*/
}
