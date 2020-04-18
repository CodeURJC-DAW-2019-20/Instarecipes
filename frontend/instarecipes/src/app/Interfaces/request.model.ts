import { User } from './user.model';

export interface Request {
  id?: number;
  username?: User;
  typeOfRequest?: string;
  ingredientContent?: string;
  cookingStyleContent?: string;
  categoryContent?: string;
  itemExists?: boolean;

}
