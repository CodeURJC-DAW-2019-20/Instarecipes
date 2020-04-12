import { Recipe } from './recipe.model';

export interface Step {
  id?: number;
  number: number;
  recipe: Recipe;
  content: string;

}
