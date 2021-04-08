import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormArray } from '@angular/forms';
import {ThemePalette} from '@angular/material/core';
export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  /*subtasks?: Task[];*/
}

@Component({
  selector: 'jhi-form-dichiarazioni',
  templateUrl: './form-dichiarazioni.component.html',
  styleUrls: ['./form-dichiarazioni.component.scss']
})
export class FormDichiarazioniComponent implements OnInit {

  @Input()  dich: FormGroup;
  allComplete: Boolean = false;
  constructor() { }

  ngOnInit() {
     this.allComplete = this.dichiarazioniFormGroup.controls.every((t: FormGroup) => t.controls['stato'].value);
  }

  get dichiarazioniFormGroup(): FormArray {
      return this.dich.get('dichiarazionis') as FormArray;
  }

  getDichiarazioniFormGroup(index: number): FormGroup {
      // this.lingueList = this.competenzeLinguisticheFormGroup;
      const formGroup = this.dichiarazioniFormGroup.controls[index] as FormGroup;
      return formGroup;
  }

  /*task: Task = {
    name: 'Indeterminate',
    completed: false,
    color: 'primary',
    subtasks: [
      {name: 'Primary', completed: false, color: 'primary'},
      {name: 'Accent', completed: false, color: 'accent'},
      {name: 'Warn', completed: false, color: 'warn'}
    ]
  };*/

  updateAllComplete() {
    // this.allComplete = this.task.subtasks != null && this.task.subtasks.every(t => t.completed);
    // console.log("updateAllComplete: ", this.dichiarazioniFormGroup.controls.every((t: FormGroup) => t.controls['stato'].value))
    this.allComplete = this.dichiarazioniFormGroup != null && this.dichiarazioniFormGroup.controls.every((t: FormGroup) => t.controls['stato'].value);
  }

  someComplete(): boolean {
    if (this.dichiarazioniFormGroup == null) {
      return false;
    }
    // return this.task.subtasks.filter(t => t.completed).length > 0 && !this.allComplete;
    return this.dichiarazioniFormGroup.controls.filter((t: FormGroup) => t.controls['stato'].value).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.dichiarazioniFormGroup == null) {
      return;
    }
    // this.task.subtasks.forEach(t => t.completed = completed);
    // console.log("completed: ", completed);
    this.dichiarazioniFormGroup.controls.forEach((t: FormGroup) => t.controls['stato'].setValue(completed));
    /*for(var i = 0; i < this.dichiarazioniFormGroup.length; i++ ) {
    	let group: FormGroup = this.getDichiarazioniFormGroup(i);
		group.controls['stato'].setErrors({required: true});
	}*/
  }
}
