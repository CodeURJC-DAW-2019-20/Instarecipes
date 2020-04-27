import {Component, OnInit} from '@angular/core';
import { RankService } from '../services/ranking.service';

@Component({
  selector: 'ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {
  rankRecipes: any[] = [];

  constructor (private rankService: RankService){ }

  ngOnInit(){
      this.get_Ranking();
  }

  get_Ranking(){
    this.rankService.getRanking().subscribe(
      recipes => {
          this.rankRecipes = recipes;
        });
      }

}
