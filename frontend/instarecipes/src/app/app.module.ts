import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

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
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BasicAuthInterceptor } from './helpers/basic-auth-interceptor';
import { AuthGuard } from './helpers/auth.guard';
import { LogoutComponent } from './logout/logout.component';
import { ErrorInterceptor } from './helpers/error.interceptor';
import { FormatTitlePipe } from './pipes/format-title.pipe';
import { ProfileComponent } from './profile/profile.component';
import { UsersComponent } from './profile/users/users.component';
import { ItemsComponent } from './profile/items/items.component';
import { signUpView } from './signUp/signUpView/signUpView.component';
import { FirstComponent } from './signUp10/first/first.component';
import { SecondComponent } from './signUp10/second/second.component';

@NgModule({

  //Aqui se deben importar los componentes
  declarations: [
    AppComponent,
    TrendingComponent,
    RecentComponent,
    IndexComponent,
    LoginComponent,
    SignUpComponent,
    RecipeSearchComponent,
    LogoutComponent,
    FormatTitlePipe,
    ProfileComponent,
    UsersComponent,
    ItemsComponent,
    signUpView,
    FirstComponent,
    SecondComponent
  ],

  //Aqui se deben importar los modulos
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule

  ],
  //Aqui se deberia poner los interceptors, como el del login y register, y tambien los services
  providers: [AuthenticationService, AuthGuard, UserService, RecipesService,
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
//@ts-ignore
export class AppModule { }
