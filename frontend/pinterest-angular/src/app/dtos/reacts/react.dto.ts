import {
    IsNotEmpty
} from 'class-validator';

export class ReactDTO {
    @IsNotEmpty()
    user_id: number;

    @IsNotEmpty()
    picture_id: number; 

    type_react: number;

    @IsNotEmpty()
    content: string;

    constructor(data: any) {
        this.user_id = data.user_id;
        this.picture_id = data.picture_id;
        this.type_react = 0;
        this.content = data.content;
    }
}