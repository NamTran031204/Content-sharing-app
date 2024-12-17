import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginDTO } from '../dtos/user/login.dto';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      rememberMe: [false]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      //console.log('Đăng nhập thành công', this.loginForm.value);
      const loginDTO: LoginDTO = {
        "email": this.loginForm.value.email,
        "password": this.loginForm.value.password,
        "rememberMe": this.loginForm.value.rememberMe
      };
    
      this.userService.login(loginDTO)
        .subscribe({
          next: (response: any) => {
            this.userService.setCurrentUser(response);
            alert('Đăng nhập thành công');
            this.router.navigate(['']);
          },
          complete: () => {
            console.log('Đăng nhập hoàn tất');
          },
          error: (error: any) => {
            console.error("Đăng nhập không thành công", error);
            alert('Đăng nhập không thành công do mật khẩu hoặc email không đúng');
          }
        });
    }
    else {
      console.log('Form không hợp lệ');
    }
  }
}
