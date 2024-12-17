import {
    IsString,
    IsNotEmpty,
    IsEmail
} from 'class-validator';

export class LoginDTO {
    @IsEmail()
    @IsNotEmpty()
    email: string;

    @IsString()
    @IsNotEmpty()
    password: string;

    rememberMe: boolean;

    constructor(data: any) {
        this.email = data.email;
        this.password = data.password;
        this.rememberMe = data.rememberMe;
    }
}