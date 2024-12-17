export interface Picture {
    id: number;
    user_id: number;
    image_url: string; 
    image_description: string; 
    title: string;
    url: string;

    //search
    tags: string[];
    //search
  }