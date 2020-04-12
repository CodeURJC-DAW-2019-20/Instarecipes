import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
//aqui ponemos los imports de otras clases para poder utilizarlos en el ruter
//tipo: import {RecipeComponent} from './Recipe/recipe.component'
import { IndexComponent } from './index/index.component'
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './signUp/signUp.component';
import { RankingComponent } from './ranking/ranking.component';
import { RecipeComponent } from './recipe/recipe.component';
import { FilteredSearchComponent } from './search/filtered-search/filtered-search.component';
import { RecipeContentComponent } from './recipe/recipe-content/recipe-content.component';

// aqui ponemos las rutas, tipo: { path: 'recipe/:id', component: RecipeComponent},{...}
const routes: Routes = [
  { path: 'index', component: IndexComponent },
  { path: '', redirectTo: 'index', pathMatch: 'full'},
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'ranking', component: RankingComponent },
  { path: 'recipe', component: RecipeComponent },
  { path: 'filtered-search', component: FilteredSearchComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
