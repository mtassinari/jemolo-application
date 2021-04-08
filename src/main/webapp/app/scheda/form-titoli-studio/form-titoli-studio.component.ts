import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ITipologia, IAnnoConseguimento } from 'app/shared/model/titolo-studio.model';
import { ErrorStateMatcher } from '@angular/material/core';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
@Component({
  selector: 'jhi-form-titoli-studio',
  templateUrl: './form-titoli-studio.component.html',
  styleUrls: ['./form-titoli-studio.component.scss']
})
export class FormTitoliStudioComponent implements OnInit {

    @Input()  studio: FormGroup;
    tipologie: ITipologia[] = [
       {value: 'diploma-laurea', viewValue: 'Diploma di laurea vecchio ordinamento di durata non inferiore a 4 anni'},
       {value: 'laurea-magistrale', viewValue: 'Laurea magistrale nuovo ordinamento di durata normale di 5 o 6 anni (a ciclo unico)'},
       {value: 'laurea-specialistica', viewValue: 'Laurea specialistica di durata normale di 2 anni (dopo aver conseguito la laurea di durata di 3 anni)'}
    ];

    matcher = new MyErrorStateMatcher();

    constructor() { }

    ngOnInit() {
    }

    arrayAnni(n: number, startFrom: number): number[] {
        return [...Array(n).keys()].map(i => i + startFrom);
    }
}
