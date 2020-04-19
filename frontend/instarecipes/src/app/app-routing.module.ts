import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndexComponent } from './index/index.component'
import { LoginComponent } from './login/login.component';
import { RankingComponent } from './ranking/ranking.component';
import { RecipeComponent } from './recipe/recipe.component';
import { FilteredSearchComponent } from './search/filtered-search/filtered-search.component';
import { ProfileComponent } from './profile/profile.component';
import { UserSearchComponent } from './search/user-search/user-search.component';
import { RecipeSearchComponent } from './search/recipe-search/recipe-search.component';
import { SignUpComponent } from './signUp10/first/signUp.component';
import { SignUpExtendedComponent } from './signUp10/second/signUpExtended.component';
import { UserComponent } from './profile/user/user.component';
import { ErrorComponent } from './error/error.component';
import { ItemsComponent } from './profile/items/items.component';

const routes: Routes = [
  { path: '', redirectTo: 'index', pathMatch: 'full'},
  { path: 'index', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'ranking', component: RankingComponent },
  { path: 'recipe', component: RecipeComponent },
  { path: 'filtered-search', component: FilteredSearchComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'signUp', component: SignUpComponent },
  { path: 'signUpExtended', component: SignUpExtendedComponent },
  { path: 'users', component: UserSearchComponent },
  { path: 'recipe-search', component: RecipeSearchComponent },
  { path: 'user-profile', component: UserComponent },
  { path: 'users/:id', component: ProfileComponent },
  { path: 'error', component: ErrorComponent },
  { path: 'hola', component:   ItemsComponent },

  { path: '**', redirectTo: 'error' }  // Wildcard route for a 404 page

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
