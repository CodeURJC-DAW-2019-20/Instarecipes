import {Component,OnInit} from '@angular/core';
import { UserService } from '../../services/user.service';
import { ProfileService } from '../../services/profile.service';
import { Router } from '@angular/router';
import { User } from '../../Interfaces/user.model';
import { DomSanitizer } from '@angular/platform-browser';
import { RecipesService } from '../../services/recipes.service';
import { Recipe } from '../../Interfaces/recipe.model';
import { isNumber } from 'util';
//import { EditProfileComponent } from '../../popup/editProfile/editProfile.component';
//import { MatDialog, MatDialogConfig } from "@angular/material";

@Component({
  selector: 'user-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {
  users: User[] = []
  avatar: any[] = [];
  image: any[] = [];
  user: User;
  recipes: any[]=[];
  href: string = "";
  parts: string[]=[];
  id_user: number=-1;
  constructor(private profileService: ProfileService,private recipesService: RecipesService,
    private domSanitizer: DomSanitizer,private router: Router){

  }

  ngOnInit(){
    this.href = this.router.url;
    this.parts=this.href.split('/');
    if(isNumber( this.parts[-1])){
      this.id_user=Number(this.parts[-1]);
      this.refresh(this.id_user);
    }
    else{
      this.refresh1();

    }
  }

  refresh(id_user : number){
    this.profileService.getUser(id_user).subscribe(
      user => {
        this.user = user;
        this.get_Recipes(id_user);
        for(var recipe of this.recipes){
          this.recipeMainImage(recipe);
        }     }
    );
  }
  refresh1(){
    this.profileService.getActualUser().subscribe(
      user => {
        this.user = user;
        this.get_Recipes(this.id_user);
        for(var recipe of this.recipes){
          this.recipeMainImage(recipe);
          this.recipeStepImage(recipe)
        }   
      }
    )
  }
  get_Recipes(id_user: number){
    this.profileService.getProfileAvatar(id_user).subscribe(
      data => {
        var urlCreator = window.URL;
        this.recipes.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }
  
  recipeMainImage(r: Recipe) {
    this.recipesService.getRecipeAvatar(r.username.id).subscribe(
      data => {
        var urlCreator = window.URL;
        this.image.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
    
  }

  
  recipeStepImage(r: Recipe) {
    this.recipesService.getRecipeStepImage(r.id, 0).subscribe(
      data => {
        var urlCreator = window.URL;
        this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }
  

  
}
