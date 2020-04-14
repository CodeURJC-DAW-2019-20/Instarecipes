import { Component, Input, Output, EventEmitter } from '@angular/core';

import { Step } from '../../../Interfaces/step.model';
import { RecipesService } from 'src/app/services/recipes.service';

@Component({
	selector: 'step',
	templateUrl: './step.component.html'
})
export class StepComponent {

	@Input()
	step: Step;
	@Input()
	i: number;

	imageStepMap: Map<number, File> = new Map();

	@Output() 
	mapEvent = new EventEmitter<Map<number, File>>();

	URL: any;

	useImage(event) {
		if (event.target.files && event.target.files[0]) {
		  const reader = new FileReader();
		  this.imageStepMap.set(this.i+2, event.target.files[0]);
		  this.mapEvent.emit(this.imageStepMap)
		  reader.readAsDataURL(event.target.files[0]); // Read file as data url
		  reader.onloadend = (e) => { // function call once readAsDataUrl is completed
			this.URL = e.target['result']; // Set image in element
		  };
		}
	}
}
