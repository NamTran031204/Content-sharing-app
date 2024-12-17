import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiRegister = 'http://localhost:8088/api/v1/users/register';
  private apiLogin = 'http://localhost:8088/api/v1/users/login';
  constructor(private http: HttpClient) { }
  register(registerDTO: RegisterDTO): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.apiRegister, registerDTO, { headers });
  }

  login(loginDTO: LoginDTO): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.apiLogin, loginDTO, { headers });
  }

  // Lấy thông tin user từ localStorage
  getCurrentUser() {
    const user = localStorage.getItem('currentUser');
    return user ? JSON.parse(user) : null;
  }

  // Lưu thông tin user vào localStorage
  setCurrentUser(user: any) {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  // Xóa thông tin user khi logout
  clearCurrentUser() {
    localStorage.removeItem('currentUser');
  }
}
