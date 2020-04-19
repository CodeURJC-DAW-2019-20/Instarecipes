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
import { RecipeSearchComponent } from './search/recipe-search/recipe-search.component';
import { AuthenticationService } from './services/authentication.service';
import { UserService } from './services/user.service';
import { RecipesService } from './services/recipes.service';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AuthGuard } from './helpers/auth.guard';
import { ErrorInterceptor } from './helpers/error.interceptor';
import { CommentsComponent } from './recipe/comments/comments.component';
import { RecipeComponent } from './recipe/recipe.component';
import { RecipeContentComponent } from './recipe/recipe-content/recipe-content.component';
import { RecipeStepsComponent } from './recipe/recipe-steps/recipe-steps.component';
import { ProfileComponent } from './profile/profile.component';
import { ItemsComponent } from './profile/items/items.component';
import { SignUpComponent } from './signUp10/first/signUp.component';
import { SignUpExtendedComponent } from './signUp10/second/signUpExtended.component';
import { AddRecipeComponent } from './index/popup/add/addRecipe.component';
import { FilterRecipeComponent } from './index/popup/filter/filterRecipe.component';
import { RankingComponent } from './ranking/ranking.component';
import { ProfileService } from './services/profile.service';
import { RequestsComponent } from './profile/requests/requests.component';
import { UserSearchComponent } from './search/user-search/user-search.component';
import { FilteredSearchComponent } from './search/filtered-search/filtered-search.component';
import { RecipeService } from './services/recipe.service';
import { StepComponent } from './index/popup/add/step.component';
import { PublicationsComponent } from './profile/user/publications/publications.component';
import { UsersListComponent } from './profile/usersList/usersList.component';
import { RecipePreviewComponent } from './profile/user/popup/recipePreview/recipePreview.component';
import { StatisticsComponent } from './profile/user/popup/statistics/statistics.component';
import { EditProfileComponent } from './profile/user/popup/editProfile/editProfile.component';
import { SendItemRequestComponent } from './profile/user/popup/sendItemRequest/sendItemRequest.component';
import { FollowersComponent } from './profile/user/popup/followers/followers.component';
import { FollowingComponent } from './profile/user/popup/following/following.component';
import { UserComponent } from './profile/user/user.component';
import { SearchService } from './services/search.service';
import { RankService } from './services/ranking.service';

@NgModule({

  //Aqui se deben importar los componentes
  declarations: [
    AppComponent,
    TrendingComponent,
    RecentComponent,
    IndexComponent,
    LoginComponent,
    RecipeSearchComponent,
    RecipeComponent,
    CommentsComponent,
    RecipeContentComponent,
    RecipeStepsComponent,
    ProfileComponent,
    ItemsComponent,
    SignUpComponent,
    SignUpExtendedComponent,
    RecipeComponent,
    AddRecipeComponent,
    RankingComponent,
    RequestsComponent,
    EditProfileComponent,
    SendItemRequestComponent,
    StatisticsComponent,
    RecipePreviewComponent,
    UserSearchComponent,
    FilteredSearchComponent,
    FilterRecipeComponent,
    StepComponent,
    PublicationsComponent,
    UsersListComponent,
    FollowersComponent,
    FollowingComponent,
    UserComponent
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
  providers: [AuthenticationService, AuthGuard, UserService, RecipesService, ProfileService, RecipeService, SearchService, RankService,
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
//@ts-ignore
export class AppModule { }
