import { Component, OnInit } from '@angular/core';
import { SearchService } from 'src/app/services/search.service';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { FilteredSearchDTO } from 'src/app/Interfaces/filteredSearchDTO.model';

@Component({
  selector: 'app-filtered-search',
  templateUrl: './filtered-search.component.html',
  styleUrls: ['./filtered-search.component.css']
})
export class FilteredSearchComponent implements OnInit {
  recipes : Recipe[] = [];

  constructor(private searchService: SearchService,) { }

  ngOnInit() {
    this.getRecipes();
  }

  getRecipes() {
    this.searchService.getFilteredRecipes().subscribe(
      recipe => {
        console.log("Recipe catched" + recipe);
      },
      (error: Error) => console.error('Error creating recipe step image: ' + error),
    );
    console.log(this.searchService.getFilteredRecipes());
  }
}
