import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AppComponent } from './app/app.component';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ImageInfoComponent } from './image-info/image-info.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

//search
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
//search

@NgModule({
  declarations: [    
    HomeComponent, 
    HeaderComponent,
    FooterComponent, 
    LoginComponent, RegisterComponent, AppComponent, ImageInfoComponent,  
    

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,

    //search
    MatFormFieldModule,
    MatInputModule
    //search
  ],
  providers: [],
  bootstrap: [
    AppComponent
    // HomeComponent
    //LoginComponent,
    //RegisterComponent
    //ProfileComponent
  ]
})
export class AppModule { 

}
