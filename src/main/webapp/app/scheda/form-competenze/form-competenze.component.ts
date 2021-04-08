import { Component, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { FormAmbitoCompetenzaService } from 'app/scheda/form-scheda/services/form-ambito-competenza.service';
import { Subscription } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';

import { IAmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { AccountService } from 'app/core/auth/account.service';
import { CustomValidators, ConfirmValidParentMatcher, MyErrorStateMatcher } from 'app/account/register-candidato/custom-validation';
import { ICompetenza } from 'app/shared/model/competenza.model';

@Component({
  selector: 'jhi-form-competenze',
  templateUrl: './form-competenze.component.html',
  styleUrls: ['./form-competenze.component.scss']
})
export class FormCompetenzeComponent implements OnInit {

    @Input() compList: FormArray;
    @Input() comp: FormGroup;
    matcher = new MyErrorStateMatcher();

    constructor(protected ambitoCompetenzaService: FormAmbitoCompetenzaService, private fb: FormBuilder) { }

    arrayAnni(n: number, startFrom: number): number[] {
        return [...Array(n).keys()].map(i => i + startFrom);
    }

    ngOnInit() { }

    // returns all form groups under competenze
    get competenzeFormGroup(): FormArray {
        return this.comp.get('competenze') as FormArray;
    }

    getCompetenzeFormGroup(index): FormArray {
        return this.getCompFormGroup(index).controls['sottoambitos'] as FormArray;
    }

    getCompFormGroup(index): FormGroup {
        // this.competenzeList = this.editForm.get('competenze') as FormArray;
        const formGroup = this.competenzeFormGroup.controls[index] as FormGroup;
        // const formGroup = this.competenzeList.controls[index] as FormGroup;
        return formGroup;
    }

    getCompFormGroup2(i_ambito, i_sottoambitos): FormGroup {
        // this.competenzeList = this.editForm.get('competenze') as FormArray;
        const formGroupAmbito = this.getCompetenzeFormGroup(i_ambito);
        const formGroupSottoAmbito = formGroupAmbito.controls[i_sottoambitos] as FormGroup;
        // const formGroup = this.competenzeList.controls[index] as FormGroup;
        return formGroupSottoAmbito;
    }

    onChange2(event, i_ambito: number, i_sottoambitos: number, item: FormGroup) {
        let validators = null;
        if (event.type === 'change' && item.controls['selected'].value === false) {
            item.controls['selected'].patchValue(true);
            this.compList.push(this.getCompFormGroup2(i_ambito, i_sottoambitos));
        }
        if (item.controls['selected'].value === true) {
            validators = Validators.compose([Validators.required, Validators.min(2)]);
            // item.controls['anniEsperianza'].enable();
            item.controls['anniEsperianza'].setValidators(validators);
            item.controls['anniEsperianza'].markAsTouched();
            item.controls['anniEsperianza'].updateValueAndValidity();
        } else {
            // validators = Validators.compose([Validators.required]);
            item.controls['anniEsperianza'].reset();
            // item.controls['anniEsperianza'].disable();
            item.controls['anniEsperianza'].setValidators(null);
            item.controls['anniEsperianza'].markAsTouched();
            item.controls['anniEsperianza'].updateValueAndValidity();
        }
        this.getCompetenzeFormGroup(i_ambito).updateValueAndValidity();
        this.compList.updateValueAndValidity();
        if (event.checked === true) {
            this.compList.push(this.getCompFormGroup2(i_ambito, i_sottoambitos));
        } else if (event.checked === false) {
            const index: number = this.compList.value.findIndex((comp: ICompetenza) => comp.ambitoComp.id === item.controls['ambitoComp'].value.id);
            console.log('index: ', index);
            this.compList.removeAt(index);
        }

    }

    onChange(event, index: number, item: FormGroup) {
        // console.log(index, event, this.getCompFormGroup(index).controls['selected'].value);
        // this.competenzeList.push(this.getCompFormGroup(index));
        let validators = null;

        if (this.getCompFormGroup(index).controls['selected'].value === true) {
            validators = Validators.compose([Validators.required, Validators.min(2)]);
            this.getCompFormGroup(index).controls['anniEsperianza'].enable();
          } else {
            validators = Validators.compose([Validators.required]);
            this.getCompFormGroup(index).controls['anniEsperianza'].reset();
            this.getCompFormGroup(index).controls['anniEsperianza'].disable();
          }

        this.getCompFormGroup(index).controls['anniEsperianza'].setValidators(validators);
        // this.competenzeFormGroup.markAsDirty();
        this.getCompFormGroup(index).controls['anniEsperianza'].markAsTouched();
        // re-validate the inputs of the form control based on new validation
        this.getCompFormGroup(index).controls['anniEsperianza'].updateValueAndValidity();
    }
}
