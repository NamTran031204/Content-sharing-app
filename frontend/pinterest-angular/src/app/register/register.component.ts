import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { RegisterDTO } from '../dtos/user/register.dto';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder,  private router: Router, private userService: UserService) {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      userName: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      retypePassword: ['', [Validators.required, Validators.minLength(6)]],
      description: '',
      acceptTerms: [false, [Validators.requiredTrue]],
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      //console.log('Đăng ký thành công', this.registerForm.value);
      
      
      const registerDTO: RegisterDTO = {
        "name": this.registerForm.value.name,
        "email": this.registerForm.value.email,
        "userName": this.registerForm.value.userName,
        "password": this.registerForm.value.password,
        "retype_password": this.registerForm.value.retypePassword,
        "description": this.registerForm.value.description,
        "role_id": 1,
        
        acceptTerms: false
      };
      this.userService.register(registerDTO)
        .subscribe({
          next: (response: any) => {
            this.router.navigate(['/login']);
          },
          complete: () => {
            console.log("Đăng kí thành công");
          },
          error: (error: any) => {
            console.error("Đăng kí không thành công",error);
          }
        });
      
    } else {
      console.log('Form không hợp lệ');
    }
  }

  // onFullNameChange(){
  //   if (this.email) {
  //     console.log(this.email);
  //   }
  // }
}
