import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ImageInfoComponent } from './image-info/image-info.component';
import { HeaderComponent } from './header/header.component';
import { AppModule } from './app.module';
import { AppComponent } from './app/app.component';
import { HomAfterSearchComponent } from './hom-after-search/hom-after-search.component';

const routes: Routes = [
  { path: '', component: HomeComponent }, 
  { path: 'image-info', component: ImageInfoComponent }, 
  { path: 'header', component: HeaderComponent},
  { path: 'searching', component: HomAfterSearchComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
