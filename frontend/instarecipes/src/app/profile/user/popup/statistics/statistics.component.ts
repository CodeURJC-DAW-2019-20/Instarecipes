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
  loadAPI: any;

  constructor( private profileService: ProfileService, private authService: AuthenticationService) { }

  ngAfterViewInit() {
    this.loadAPI = new Promise(resolve => {
      console.log("resolving promise...");
      this.loadScript();
    });
  }

  ngOnInit() {
    this.getAllRecipes();
  }

  public loadScript() {
    console.log("preparing to load...");
    let node = document.createElement("script");
    node.src = 'assets/js/statistics.js';
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
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
