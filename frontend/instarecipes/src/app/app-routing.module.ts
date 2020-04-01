import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
//aqui ponemos los imports de otras clases para poder utilizarlos en el ruter
//tipo: import {RecipeComponent} from './Recipe/recipe.component'
import { IndexComponent } from './index/index.component'
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './signUp/signUp.component';
import { RankingComponent } from './ranking/ranking.component';
import { ProfileComponent } from './profile/profile.component';
import { signUpView } from './signUp/signUpView/signUpView.component';
import { FirstComponent } from './signUp10/first/first.component';
import { SecondComponent } from './signUp10/second/second.component';
import { SignUp10Component } from './signUp10/signUp10.component';

// aqui ponemos las rutas, tipo: { path: 'recipe/:id', component: RecipeComponent},{...}
const routes: Routes = [
  { path: '', redirectTo: 'index', pathMatch: 'full'},
  { path: 'index', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'ranking', component: RankingComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'signupview', component: signUpView },
  { path: 'first', component: FirstComponent },
  { path: 'second', component: SecondComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
