import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HomeComponent } from './home/home.component';
import { ImageInfoComponent } from './image-info/image-info.component';
import { HeaderComponent } from './header/header.component';


@NgModule({
  declarations: [
    HomeComponent,
    ImageInfoComponent,
    HeaderComponent
    
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [
     HomeComponent
    //  ImageInfoComponent
  ]
})
export class AppModule { }
