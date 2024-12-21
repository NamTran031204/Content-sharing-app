//Duy
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/environment';
import { ReactDTO } from '../dtos/reacts/react.dto';

@Injectable({
  providedIn: 'root'
})
export class ReactService {
  private apiUrl = `${environment.apiBaseUrl}/reacts`;

  constructor(private http: HttpClient) {}

  like(reactDTO: ReactDTO): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    reactDTO.type_react = 0;
    return this.http.post(`${this.apiUrl}`, reactDTO, { headers });
  }

  unlike(reactDTO: ReactDTO, picture_id: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    reactDTO.type_react = 0;
    return this.http.delete(`${this.apiUrl}/${picture_id}`, { headers, body: reactDTO });
  }

  comment(reactDTO: ReactDTO): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    reactDTO.type_react = 2;
    return this.http.post(`${this.apiUrl}`, reactDTO, { headers });
  }
  
  getReacts(pictureId: number, page: number, limit: number): Observable<any> {
    const params = new HttpParams()
          .set('page', page.toString())
          .set('limit', limit.toString());
    return this.http.get(`${this.apiUrl}/${pictureId}/2`, {params});
  }

  getTotalLike(pictureId: number, page: number, limit: number): Observable<any> {
    const params = new HttpParams()
          .set('page', page.toString())
          .set('limit', limit.toString());
    return this.http.get(`${this.apiUrl}/${pictureId}/0`, {params});
  }

}