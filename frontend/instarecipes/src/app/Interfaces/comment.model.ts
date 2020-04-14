import { User } from './user.model';
import { Recipe } from './recipe.model';

export interface Comment{
  id?: number;
  username: User;
  content: string;
  hasSubcomments: boolean;
  isSubcomment: boolean;
  likes?: number;
  recipe: Recipe;


  subComment: Comment [];
  usersLiked: User [];
}
