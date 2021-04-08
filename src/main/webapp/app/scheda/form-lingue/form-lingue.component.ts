import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl, FormArray, ValidatorFn } from '@angular/forms';
import { LinguaService } from 'app/entities/lingua/lingua.service';
import { ILingua } from 'app/shared/model/lingua.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { ILivello } from 'app/shared/model/competenze-lng.model';

@Component({
  selector: 'jhi-form-lingue',
  templateUrl: './form-lingue.component.html',
  styleUrls: ['./form-lingue.component.scss']
})
export class FormLingueComponent implements OnInit {

    @Input()  lng: FormGroup;
    linguas: ILingua[];
    linguaue: ILingua[];
    public lingueList: FormArray;

    livelli: ILivello[] = [
       {value: 1, viewValue: 'A1'},
       {value: 2, viewValue: 'A2'},
       {value: 3, viewValue: 'B1'},
       {value: 4, viewValue: 'B2'},
       {value: 5, viewValue: 'C1'},
       {value: 6, viewValue: 'C2'}
     ];

  constructor(private fb: FormBuilder, protected linguaService: LinguaService) { }

  ngOnInit() {
      this.loadLingue();
      this.loadLingueue();
      this.lingueList = this.lng.get('lingue') as FormArray;
  }

  // returns all form groups under competenzeLinguistiche
  get competenzeLinguisticheFormGroup(): FormArray {
      return this.lng.get('lingue') as FormArray;
  }

  get isLinguaAccepted() {
      return this.lng.get('linguaita');
  }

  // add a lingua form group
  addLingua() {
    this.lingueList.push(this.createLinguaForm());
  }

  // remove lingua from group
  removeLingua(index) {
    this.lingueList.removeAt(index);
  }

  getCompFormGroup(index): FormGroup {
      // this.competenzeList = this.editForm.get('competenze') as FormArray;
      const formGroup = this.competenzeLinguisticheFormGroup.controls[index] as FormGroup;
      // const formGroup = this.competenzeList.controls[index] as FormGroup;
      return formGroup;
  }

  createLinguaForm(): FormGroup {
      return this.fb.group({
          id: [],
          livello: [null, [Validators.required]],
          lingua: [null, [Validators.required]]
      });
  }

  loadLingueue(): ILingua[] {
      this.linguaService
      .queryUe()
      .pipe(
        filter((res: HttpResponse<ILingua[]>) => res.ok),
        map((res: HttpResponse<ILingua[]>) => res.body)
      )
      .subscribe((res: ILingua[]) => {
        this.linguaue = res;
      });
    return this.linguaue;
  }

  loadLingue(): ILingua[] {
      this.linguaService
      .queryV2()
      .pipe(
        filter((res: HttpResponse<ILingua[]>) => res.ok),
        map((res: HttpResponse<ILingua[]>) => res.body)
      )
      .subscribe((res: ILingua[]) => {
        this.linguas = res;
      });
    return this.linguas;
  }

  compareLng(t1: ILingua, t2: ILingua): boolean {
      return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }

  trackLinguaById(index: number, item: ILingua) {
      console.log('item: ', item.id);
      return item.id;
    }

}
