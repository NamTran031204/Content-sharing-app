import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { HomeComponent } from './home/home.component';
import { ImageInfoComponent } from './image-info/image-info.component';
import { HeaderComponent } from './header/header.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app/app.component';
import { HomAfterSearchComponent } from './hom-after-search/hom-after-search.component';



@NgModule({
  declarations: [
    HomeComponent,
    ImageInfoComponent,
    HeaderComponent,
    AppComponent,
    HomAfterSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [
    // HomeComponent
    // ImageInfoComponent
    AppComponent
    // HomAfterSearchComponent
  ]
})
export class AppModule { }
