import {Component, OnInit} from '@angular/core';
import { RankingRecipe } from '../Interfaces/rankRecipe.model';
import { RankService } from '../services/ranking.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-home',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {
  recipes: RankingRecipe[] = [];
  constructor (private RankService: RankService, 
    private domSanitizer: DomSanitizer){ }
  ngOnInit(){
      this.get_Ranking();
    }
  get_Ranking(){
    this.RankService.get().subscribe(
      recipes => {
          this.recipes = recipes;
        });
      }
}