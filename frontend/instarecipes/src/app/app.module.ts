import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TrendingComponent } from './index/trending/trending.component';
import { RecentComponent } from './index/recent/recent.component';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './signUp/signUp.component';
import { RecipeSearchComponent } from './search/recipe-search/recipe-search.component';
import { AuthenticationService } from './services/authentication.service';
import { UserService } from './services/user.service';
import { RecipesService } from './services/recipes.service';

@NgModule({

  //Aqui se deben importar los componentes
  declarations: [
    AppComponent,
    TrendingComponent,
    RecentComponent,
    IndexComponent,
    LoginComponent,
    SignUpComponent,
    RecipeSearchComponent
  ],

  //Aqui se deben importar los modulos
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,

  ],
  providers: [AuthenticationService, UserService, RecipesService], //Aqui se deberia poner los interceptors, como el del login y register, y tambien los services
  bootstrap: [AppComponent]
})
//@ts-ignore
export class AppModule { }
