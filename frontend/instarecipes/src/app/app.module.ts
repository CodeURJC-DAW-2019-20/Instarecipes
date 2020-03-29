import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';

@NgModule({

  //Aqui se deben importar los componentes
  declarations: [
    AppComponent,
    LoginComponent,
    IndexComponent
  ],

  //Aqui se deben importar los modulos
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [], //Aqui se deberia poner los interceptors, como el del login y register
  bootstrap: [AppComponent]
})
//@ts-ignore
export class AppModule { }
