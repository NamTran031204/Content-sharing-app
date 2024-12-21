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
import { FormsModule } from '@angular/forms';
import { LucideAngularModule, Heart } from 'lucide-angular';

//search
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { UserProfileComponent } from './user-profile/user-profile.component';
//search

@NgModule({
  declarations: [    
    HomeComponent, 
    HeaderComponent,
    FooterComponent, 
    LoginComponent, RegisterComponent, AppComponent, ImageInfoComponent, UserProfileComponent,  

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    LucideAngularModule.pick({ Heart }),

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
    // ImageInfoComponent
  ]
})
export class AppModule { 

}
