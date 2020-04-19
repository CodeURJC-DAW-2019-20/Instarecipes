import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ProfileService } from 'src/app/services/profile.service';
import { AuthenticationService } from 'src/app/services/authentication.service.js';
import { Recipe } from 'src/app/Interfaces/recipe.model.js';

@Component({
  selector: 'popup-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit, AfterViewInit {
  likes: number[] = [];
  recipesTitles: string[] = [];
  recipesLikes: number[] = [];
  recipes: Recipe[] = [];
  constructor( private profileService: ProfileService, private authService: AuthenticationService) { }

  ngAfterViewInit() {
    import("../../../../../assets/js/statistics.js");
  }

  ngOnInit() {
    this.getAllRecipes();
  }

  getAllRecipes() {
    this.profileService.getUserRecipes(this.authService.user.id).subscribe(
      recipe => {
        this.recipes = recipe as Recipe[];
        this.recipes.forEach(element => {
          this.getTheTitle(element);
          this.getTheLikes(element);
        })
      });
  }

  getTheLikes(r: Recipe) {
    this.recipesLikes.push(r.likes);
  }
  getTheTitle(r: Recipe) {
    this.recipesTitles.push(r.title);
  }
}
