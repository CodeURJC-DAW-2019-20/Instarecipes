import {Component, OnInit, ViewChild, ElementRef, OnChanges} from '@angular/core';
import { CookingStyle } from 'src/app/Interfaces/cookingStyle.model';
import { ProfileService } from 'src/app/services/profile.service';
import { Ingredient } from 'src/app/Interfaces/ingredient.model';
import { Category } from 'src/app/Interfaces/category.model';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RecipeDTO } from 'src/app/Interfaces/recipeDTO.model.js';
import { RecipesService } from 'src/app/services/recipes.service.js';
import { Step } from 'src/app/Interfaces/step.model.js';

@Component({
  selector: 'popup-add',
  templateUrl: './addRecipe.component.html',
  styleUrls: ['./addRecipe.component.css']
})
export class AddRecipeComponent implements OnInit, OnChanges{

  stepsList: Step[] = [];

  recipe: RecipeDTO;
  allCookingStyles: CookingStyle[] = [];
  allIngredients: Ingredient[] = [];
  allCategories: Category[] = [];
  allAllergens: Allergen[] = [];

  catString: string = '';
  ingString: string = '';

  @ViewChild('ingredientsString') ingredientsString: ElementRef;
  @ViewChild('categoriesString') categoriesString: ElementRef;
  @ViewChild('ingredientsList') ingList: ElementRef;
  @ViewChild('categoriesList') catList: ElementRef;
  @ViewChild('otherSteps') otherSteps: ElementRef;
  @ViewChild('otherImages') otherImages: ElementRef;
  @ViewChild('mainImage') mainImage: ElementRef;

  @ViewChild('closebutton') closebutton: ElementRef;

  firstStepAux: string = '';
  allergenAux: string = '';
  withImageAux: string[] = [];
  stepsAux: string[] = [];
  ingredientsAux: string[] = [];
  categoriesAux: string[] = [];
  cookingStylesAux: string = '';
  fileToUpload: File = null;
  stepsFiles: Map<number,File> = new Map();

  loadAPI: any;

  constructor (private profileService: ProfileService, private recipesService: RecipesService,
    public authService: AuthenticationService) {
      this.initConstructor();
  }

  initConstructor(){
    this.recipe = { user: null, title: '', description: '', duration: '', difficulty: '', firstStep: '',
    allergen:'', withImage: [], steps: [], ingredients: [], categories: [], cookingStyles: [] };
    this.fileToUpload = null;
    this.stepsFiles = new Map();
    this.stepsList = [];
  }

  ngOnChanges() {
    this.recipesService.refreshRecipes(2).subscribe(
      _ => {
        console.log('hola refresh');
      });
    console.log('chjanges 2');
  }

  ngOnInit() {
    this.loadStuff();
  }

  receiveMap($event) {
    $event.forEach((value, key) => {
      console.log('Value: ' + value + ' Key: ' + key);
      this.stepsFiles.set(key, value);
    });
  }

  loadStuff(){
    this.profileService.getAllCookingStyles().subscribe(
      cookingStyles => this.allCookingStyles = cookingStyles
    );
    this.profileService.getAllIngredients().subscribe(
      ingredients => {
        this.allIngredients = ingredients;
        this.allIngredients.forEach(element => this.ingString = this.ingString + element.ingredient + ',');
        this.ingList.nativeElement.setAttribute('value', this.ingString);
        this.profileService.getAllCategories().subscribe(
          categories => {
            this.allCategories = categories;
            this.allCategories.forEach(element => this.catString = this.catString + element.category + ',');
            this.catList.nativeElement.setAttribute('value', this.catString);
            this.loadAPI = new Promise(resolve => {
              console.log('resolving promise...');
              this.loadScript();
            });
          }
        );
      }
    );
    this.profileService.getAllAllergens().subscribe(
      allergens => this.allAllergens = allergens
    );
  }

  public loadScript() {
    console.log('preparing to load...');
    let node = document.createElement('script');
    node.src = 'assets/js/add_recipe.js';
    node.type = 'text/javascript';
    node.async = true;
    node.charset = 'utf-8';
    document.getElementsByTagName('head')[0].appendChild(node);
  }

  starClick(i: number){
    switch (i) {
      case 1:
        this.recipe.difficulty = 'Easy';
        break;
      case 2:
        this.recipe.difficulty = 'Medium';
        break;
      case 3:
        this.recipe.difficulty = 'Hard';
        break;
      case 4:
        this.recipe.difficulty = 'Extreme';
        break;
      default:
        this.recipe.difficulty = '';
        break;
    }
  }

  addStep(description: string) {
    let s = {content: description, number: this.stepsList.length+1};
    this.stepsList.push(s);
    console.log('Added new step');
	}

	removeStep() {
    this.stepsList.pop();
    console.log('Removed last step');
	}

  handleFileInput(files: FileList) {
    this.fileToUpload = files[0];
  }

  postRecipe(){
    this.recipe.user = this.authService.user;
    this.recipe.ingredients.push(this.ingredientsString.nativeElement.value);
    this.recipe.categories.push(this.categoriesString.nativeElement.value);
    this.recipe.cookingStyles.push(this.cookingStylesAux);
    this.recipe.steps.push(this.otherSteps.nativeElement.value);
    this.recipe.withImage.push(this.otherImages.nativeElement.value);
    console.log(this.recipe);
    console.log(this.fileToUpload);
    this.recipesService.postRecipe(this.recipe).subscribe(
      _ =>{
        console.log('Posted recipe: '+ JSON.stringify(_));
        this.recipesService.getLastRecipeId().subscribe(
          lastId => {
            console.log('Last id: ' + lastId);
            this.recipesService.postImageStep(this.fileToUpload, lastId, 1).subscribe(
              _ => {  },
              error => {
                if (this.stepsFiles.size < 1){
                  this.initConstructor();
                  this.recipesService.refreshRecipes(2).subscribe(
                    _ => {
                    });
                  this.recipesService.triggerSomeEvent(Date());
                  this.closebutton.nativeElement.click();
                } else {
                  this.stepsFiles.forEach((value, key) => {
                    this.recipesService.postImageStep(value, lastId, key).subscribe(
                      what=> console.log('Imagen del paso ' + key + 2 + ' subida exitosamente: ' + value),
                      error3 => {
                        console.error('Error creating others recipe steps images');
                        let aux = key - 1;
                        if (aux  === this.stepsFiles.size) {
                          this.initConstructor();
                          this.closebutton.nativeElement.click();
                          this.recipesService.refreshRecipes(2).subscribe(
                            _ => {
                            });
                          this.recipesService.triggerSomeEvent(Date());
                          this.mainImage.nativeElement.setAttribute('src', '');
                        }
                      }
                    );
                  });
                }
              });
          },
          error => {console.log('Error getting last id of recipe')}
        );
      },
      error => {console.log('Error posting a recipe')}
    );
  }
}
