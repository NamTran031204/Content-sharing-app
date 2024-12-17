import {
    IsString,
    IsNotEmpty,
    IsEmail
} from 'class-validator';

export class RegisterDTO {
    @IsString()
    name: string;

    @IsEmail()
    @IsNotEmpty()
    email: string;

    @IsString()
    userName: string;

    @IsString()
    @IsNotEmpty()
    password: string;

    @IsString()
    @IsNotEmpty()
    retype_password: string;

    @IsString()
    description: string;
    acceptTerms: boolean;
    role_id: number = 1;
    constructor(data: any) {
        this.name = data.name;
        this.email = data.email;
        this.userName = data.userName;
        this.password = data.password;
        this.retype_password = data.retypePassword;
        this.description = data.description;
        this.acceptTerms = data.acceptTerms;
        this.role_id = data.role_id || 1;
    }
}