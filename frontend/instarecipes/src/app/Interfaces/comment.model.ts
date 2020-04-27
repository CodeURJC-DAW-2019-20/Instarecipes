import { User } from './user.model';
import { Recipe } from './recipe.model';

export interface Comment{
  id?: number;
  userComment?: User;
  content?: string;
  hasSubcomments?: boolean;
  isSubcomment?: boolean;
  likes?: number;
  recipe?: Recipe;
  liked?: boolean;


  subComments?: Comment [];
  usersLiked?: User [];
}
