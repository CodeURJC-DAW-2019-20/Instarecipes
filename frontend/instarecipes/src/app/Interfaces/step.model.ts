import { Recipe } from './recipe.model';

export interface Step{
    id_step?: number;
    recipe?: Recipe;
    number: number;
    content: string;
    image?: boolean;
    stepImage?: any[];
}
