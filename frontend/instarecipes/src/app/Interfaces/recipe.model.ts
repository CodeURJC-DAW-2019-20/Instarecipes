//En las interfaces se detallan los datos de cada objeto que devolvemos/recogemos de la plantilla
import { User } from './user.model';
import { CookingStyle } from './cookingStyle.model';
import { Allergen } from './allergen.model';
import { Ingredient } from './ingredient.model';
import { Category } from './category.model';


export interface Recipe {
    id?: number;
    username: User;
    title: string;
    description: string;
    likes?: number;
    n_comments?: number;
    duration: string;
    difficulty: string;

    cookingStyles?: CookingStyle [];
    allergens?: Allergen [];
    ingredients: Ingredient [];
    categories: Category [];
    likesUsers?: User [];
}
