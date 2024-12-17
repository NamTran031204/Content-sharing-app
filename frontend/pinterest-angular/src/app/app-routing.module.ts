import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AppComponent } from './app/app.component';
import { RouterModule, Routes } from '@angular/router';

import { ImageInfoComponent } from './image-info/image-info.component';


const routes: Routes = [
    {path: '',component:HomeComponent},
    {path: 'login',component:LoginComponent},
    {path: 'register',component:RegisterComponent},
    {path: 'picture/:id', component: ImageInfoComponent }
        
];


@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports:[RouterModule]
})
export class AppRoutingModule{ }