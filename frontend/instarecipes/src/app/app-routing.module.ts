import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
//aqui ponemos los imports de otras clases para poder utilizarlos en el ruter
//tipo: import {RecipeComponent} from './Recipe/recipe.component'
import { IndexComponent } from './index/index.component'
import { LoginComponent } from './login/login.component';
import { RankingComponent } from './ranking/ranking.component';
import { ProfileComponent } from './profile/profile.component';
import { FirstComponent } from './signUp10/first/first.component';
import { SecondComponent } from './signUp10/second/second.component';
import { Recipe_contentComponent } from './recipe/recipe-content/recipe-content.component';
import { RecipeComponent } from './recipe/recipe.component';
import { UserSearchComponent } from './search/user-search/user-search.component';
import { RecipeSearchComponent } from './search/recipe-search/recipe-search.component';
import { SearchComponent } from './search/search.component';
import { FilteredSearchComponent } from './search/filtered-search/filtered-search.component';

// aqui ponemos las rutas, tipo: { path: 'recipe/:id', component: RecipeComponent},{...}
const routes: Routes = [
  { path: '', redirectTo: 'index', pathMatch: 'full'},
  { path: 'index', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'ranking', component: RankingComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'first', component: FirstComponent },
  { path: 'second', component: SecondComponent },
  { path: 'recipe', component: RecipeComponent },
  { path: 'users', component: UserSearchComponent },
  { path: 'recipe-search', component: RecipeSearchComponent },
  { path: 'user-search', component: UserSearchComponent },
  { path: 'filtered-search', component: FilteredSearchComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
