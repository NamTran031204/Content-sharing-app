import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http'
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/environment';
import { Picture } from '../models/picture';

@Injectable({
  providedIn: 'root'
})
export class PictureService {
  private apiGetPicture = `${environment.apiBaseUrl}/pictures`;

  constructor(private http:HttpClient) { }

  getPicture(page: number, limit: number): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());
    return this.http.get<any>(`${environment.apiBaseUrl}/pictures`, { params });
  }

  getPictureById(pictureId: number): Observable<Picture> {
    return this.http.get<Picture>(`${environment.apiBaseUrl}/pictures/getPicture/${pictureId}`);
  }

  //search
  private apiSearchPictures = `${environment.apiBaseUrl}/pictures/search`;
  searchPictures(query: string): Observable<Picture[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<Picture[]>(this.apiSearchPictures, { params });
  }
  //search
  
}
