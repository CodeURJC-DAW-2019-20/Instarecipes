import { User } from './user.model';

export interface RecipeDTO {
    user: User;
    title: string;
    description: string;
    duration: string;
    difficulty: string;
    firstStep: string;
    allergen: string;
    withImage: string[];
    steps: string[];
    ingredients: string[];
    categories: string[];
    cookingStyles: string[];
}
