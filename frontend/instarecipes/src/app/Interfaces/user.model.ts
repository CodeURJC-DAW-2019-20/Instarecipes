
export interface User{
    id?: number;
    username: string;
    email: string;
    passwordHash?: string;
    roles: string[];
    authdata?: string;
    name: string;
    surname: string;
    allergen: string;
}