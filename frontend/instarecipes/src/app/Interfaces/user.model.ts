
export interface User{
    id?: number;
    username: string;
    email: string;
    password?: string;
    roles?: string[];
    authdata?: string;
    name: string;
    surname: string;
    allergens: string;
    followingNum?: number;
    followersNum?: number;
    info: string;
}
