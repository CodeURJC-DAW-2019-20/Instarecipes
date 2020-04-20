import { Component, OnInit, Input } from '@angular/core';
import { ProfileService } from 'src/app/services/profile.service';
import { Recipe } from 'src/app/Interfaces/recipe.model.js';

@Component({
  selector: 'popup-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  likes: number[] = [];
  recipesTitles: string[] = [];
  recipesLikes: number[] = [];
  recipes: Recipe[] = [];
  loadAPI: any;

  @Input()
  id_user: number;

  constructor(private profileService: ProfileService) { }

  ngOnInit() {
    this.getAllRecipes();
  }

  getAllRecipes() {
    console.log("ID de statistics: " + this.id_user);
    this.profileService.getUserRecipes(this.id_user).subscribe(
      recipe => {
        console.log("llego a statistics, deberia mostrarse el grafico");
        this.recipes = recipe as Recipe[];
        this.recipes.forEach(element => {
          this.getTheTitle(element);
          this.getTheLikes(element);
        });
        this.loadAPI = new Promise(resolve => {
          console.log("resolving promise...");
          this.loadScript('assets/js/statistics.js');
        });
      });
  }

  public loadScript(url: any) {
    console.log("loading script...");
    let prev_node = document.getElementById(url);
    if(prev_node != null){
      prev_node.parentNode.removeChild(prev_node);
    }
    let node = document.createElement("script");
    node.src = url;
    node.id = url;
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
  }

  getTheLikes(r: Recipe) {
    this.recipesLikes.push(r.likes);
  }
  getTheTitle(r: Recipe) {
    this.recipesTitles.push(r.title);
  }
}
